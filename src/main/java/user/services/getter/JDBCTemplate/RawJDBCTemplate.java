package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import user.services.getter.model.LogRaw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
public class RawJDBCTemplate {

    private static final Logger logger = LoggerFactory.getLogger(FileDateTimeMapperJDBCTemplate.class);

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Collection<LogRaw> getLogs(Integer requestId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT FROM_UNIXTIME(UNIXSEC) AS UNIXSEC, SRCADDR, SRCPORT, DSTADDR, DSTPORT, NATADDR, DOCTETS " +
                "FROM getter_raw_");
        sb.append(requestId);
        sb.append(";");

        List<LogRaw> logRaws = jdbcTemplate.query(sb.toString(), new LogRowRowMapper());

        return new HashSet<>(logRaws);

    }

    public void save(Integer requestId, Collection<LogRaw> logs) {
        if (logs != null && logs.size() > 0) {
            jdbcTemplate.update("DROP TABLE IF EXISTS `getter_raw_" + requestId + "`; ");
            String SQL =
                    "CREATE TABLE IF NOT EXISTS `getter_raw_" + requestId + "` ( " +
                    "  `UNIXSEC` int(12) UNSIGNED DEFAULT NULL, " +
                    "  `SRCADDR` int(12) UNSIGNED DEFAULT NULL, " +
                    "  `SRCPORT` int(12) DEFAULT NULL, " +
                    "  `DSTADDR` int(12) UNSIGNED DEFAULT NULL, " +
                    "  `DSTPORT` int(12) DEFAULT NULL, " +
                    "  `NATADDR` int(12) UNSIGNED DEFAULT NULL, " +
                    "  `DOCTETS` int(12) DEFAULT NULL " +
                    ") ENGINE=MyISAM DEFAULT CHARSET=utf8; ";
            jdbcTemplate.update(SQL);
            SQL = "INSERT INTO getter_raw_" + requestId + "(UNIXSEC, SRCADDR, SRCPORT, DSTADDR, DSTPORT, NATADDR, " +
                    "DOCTETS) VALUES (?, ?, ?, ?, ?, ?, ?)";
            for (LogRaw lr : logs) {

                Timestamp timestamp = Timestamp.valueOf(lr.getDateTime());
                jdbcTemplate.update(SQL, new Object[]{timestamp
                        , lr.getSrcIp()
                        , lr.getSrcPort()
                        , lr.getDstIp()
                        , lr.getDstPort()
                        , lr.getNatIp()
                        , lr.getBytes()});
            }
        }
    }

    public Long getRawCount(Integer requestId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(UNIXSEC) FROM getter_raw_");
        sb.append(requestId);
        sb.append(";");

        return jdbcTemplate.queryForObject(sb.toString(),Long.class);
    }

    public Collection<LogRaw> getLogsRange(Integer requestId, Long ofset, Long limit) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT FROM_UNIXTIME(UNIXSEC) AS UNIXSEC, SRCADDR, SRCPORT, DSTADDR, DSTPORT, NATADDR, DOCTETS " +
                "FROM getter_raw_");
        sb.append(requestId);
        sb.append(" Limit ?,?;");

        List<LogRaw> logRaws = jdbcTemplate.query(sb.toString(), new Object[]{ofset,limit}, new LogRowRowMapper() );

        return new HashSet<>(logRaws);

    }

    class LogRowRowMapper implements RowMapper<LogRaw> {

        @Override
        public LogRaw mapRow(ResultSet rs, int rowNum) throws SQLException {
            LogRaw logRaw = new LogRaw(rs.getTimestamp("UNIXSEC").toLocalDateTime(),
                    rs.getLong("SRCADDR"),
                    rs.getInt("SRCPORT"),
                    rs.getLong("DSTADDR"),
                    rs.getInt("DSTPORT"),
                    rs.getLong("NATADDR"),
                    rs.getInt("DOCTETS"));

            return logRaw;
        }
    }
}
