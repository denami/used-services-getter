package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ReportJDBCTemplate{

    private static final Logger logger = LoggerFactory.getLogger(FileDateTimeMapperJDBCTemplate.class);

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Integer requestId, Integer userId, LocalDateTime dateTime, Long srcIp, Long dstIp, Integer bytes) {
        String SQL = "INSERT INTO getter_report (request_id, user_id, data_time, SRCADDR, DSTADDR, DOCTETS) " +
                "VALUES (?, ?, ?, INET_NTOA( ? ), INET_NTOA( ? ), ?)";

        jdbcTemplate.update(SQL, new Object[]{
                requestId,
                userId,
                dateTime.format(dtf),
                srcIp,
                dstIp,
                bytes});

    }
}
