package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.FileDateTimeMapperJDBCTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FileDateTimeMapperDaoImpl implements FileDateTimeMapperDao {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("'nfcapd.'yyyyMMddHHmm");

    @Value("${file.date.time.parse.range}")
    Integer secondsParseRange;

    @Autowired
    FileDateTimeMapperJDBCTemplate fileDateTimeMapperJDBCTemplate;

    @Override
    public Collection<String> getAllFiles() {
        return null;
    }

    @Override
    public Collection<String> getFilesForDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay().minusSeconds(secondsParseRange);
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX).plusSeconds(secondsParseRange);
        Map<String,LocalDateTime> fileMap = fileDateTimeMapperJDBCTemplate.getFilesForDateTimeRange(startDateTime, endDateTime);
        return fileMap.keySet();
    }

    @Override
    public void save(Collection<String> files) {
        Map<String, LocalDateTime> fileMap = new HashMap<>();
        for (String s : files){
            LocalDateTime localDateTime = LocalDateTime.parse(s,dtf);
            fileMap.put(s,localDateTime);
        }
        fileDateTimeMapperJDBCTemplate.save(fileMap);
    }
}
