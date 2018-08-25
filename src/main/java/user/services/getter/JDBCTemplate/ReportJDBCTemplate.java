package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import user.services.getter.model.Report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
public class ReportJDBCTemplate{

    private static final Logger logger = LoggerFactory.getLogger(FileDateTimeMapperJDBCTemplate.class);

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Integer requestId, Integer userId, LocalDateTime dateTime, Long srcIp, Long dstIp,
                     Long natIp, Integer bytes) {
        String SQL = "INSERT INTO getter_report (request_id, user_id, data_time, SRCADDR, DSTADDR, DOCTETS, NATADDR) " +
                "VALUES (?, ?, ?, INET_NTOA( ? ), INET_NTOA( ? ), ?, INET_NTOA( ? ))";

        jdbcTemplate.update(SQL, new Object[]{
                requestId,
                userId,
                dateTime.format(dtf),
                srcIp,
                dstIp,
                natIp,
                bytes});

    }

    public void cleanReport(Integer requestId) {
        String SQL = "DELETE FROM getter_report WHERE request_id = ?";
        jdbcTemplate.update(SQL, new Object[]{requestId});
    }

    public Collection<Report> getReports(Integer requestId) {

        String SQL = "SELECT request_id, user_id, data_time, SRCADDR, DSTADDR, NATADDR, DOCTETS " +
                "FROM getter_report WHERE request_id = ?";
        List<Report> reports ;
        try {
            reports = jdbcTemplate.query(SQL, new Object[]{requestId},
                    new RowMapper<Report>() {
                        @Override
                        public Report mapRow(ResultSet rs, int rowNum) throws SQLException {

                            Integer request_id = rs.getInt("request_id");
                            Integer user_id = rs.getInt("user_id");
                            LocalDateTime time = rs.getTimestamp("data_time").toLocalDateTime();
                            String srcIp = rs.getString("SRCADDR");
                            String dstIp = rs.getString("DSTADDR");
                            String natIp = rs.getString("NATADDR");
                            Integer bytes = rs.getInt("DOCTETS");

                            return new Report(request_id, user_id, time, dstIp, srcIp, natIp, bytes);
                        }
                    });
            return new HashSet<>(reports);

        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.warn(emptyResultDataAccessException.getLocalizedMessage());
        }
        return null;
    }
}
