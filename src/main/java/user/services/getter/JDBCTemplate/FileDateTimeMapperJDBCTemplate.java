package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class FileDateTimeMapperJDBCTemplate {

    private static final Logger logger = LoggerFactory.getLogger(FileDateTimeMapperJDBCTemplate.class);

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, LocalDateTime> getAllFiles() {
        Map<String, LocalDateTime> allFiles = new HashMap<>();
        String SQL="SELECT id,name,start_datetime FROM getter_file_datetime ORDER BY start_datetime";
        List<FileDate> fileDatesList = jdbcTemplate.query(SQL, new FileDataTimeRowMapper());
        for (FileDate fileDate : fileDatesList ){
            allFiles.put(fileDate.getName(),fileDate.getLocalDateTime());
        }
        return allFiles;
    }

    public Map<String, LocalDateTime> getFilesForDateTimeRange(LocalDateTime startLocalDate,
                                                               LocalDateTime endLocalDate) {
        Map<String, LocalDateTime> filesMap = new HashMap<>();
        String SQL="SELECT id," +
                "name," +
                "start_datetime " +
                "FROM getter_file_datetime " +
                "WHERE start_datetime > ? and start_datetime < ? " +
                "ORDER BY start_datetime";
        List<FileDate> fileDatesList = jdbcTemplate.query(
                SQL,
                new Object[]{startLocalDate.format(dtf), endLocalDate.format(dtf)},
                new FileDataTimeRowMapper());

        for (FileDate fileDate : fileDatesList){
            filesMap.put(fileDate.getName(),fileDate.getLocalDateTime());
        }
        return filesMap;
    }

    public void save(Map<String, LocalDateTime> fileMap) {
        String SQL = "REPLACE INTO getter_file_datetime (name, start_datetime) " +
                "VALUES (?, ?);";
        Set<String> keySet = fileMap.keySet();
        for (String key : keySet) {
            jdbcTemplate.update(SQL, new Object[]{key,fileMap.get(key)});
        }
        Set<String> all = getAllFiles().keySet();
        all.removeAll(fileMap.keySet());
        logger.info("To remove: {}", all);

    }

    class FileDate {

        private String name;
        private LocalDateTime localDateTime;

        public FileDate(){};

        public FileDate(String name, LocalDateTime localDateTime) {
            this.name = name;
            this.localDateTime = localDateTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }
    }

    class FileDataTimeRowMapper implements RowMapper<FileDate> {
        @Override
        public FileDate mapRow(ResultSet rs, int rowNum) throws SQLException {
            FileDate fileDate = new FileDate(
                    rs.getString("name"),
                    rs.getTimestamp("start_datetime").toLocalDateTime());
            return fileDate;
        }
    }
}
