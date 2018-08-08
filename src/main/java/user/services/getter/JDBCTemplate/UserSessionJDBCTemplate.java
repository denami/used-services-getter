package user.services.getter.JDBCTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import user.services.getter.model.UserSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UserSessionJDBCTemplate {

    private static final Logger logger = LoggerFactory.getLogger(UserSessionJDBCTemplate.class);

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserSession getUserByLogin(Long ip, LocalDateTime startDateTime) {

        String SQL = "SELECT id, " +
                "INET_ATON(ip) as long_ip, " +
                "starttime, " +
                "stoptime, " +
                "account FROM getter_history WHERE ip = INET_NTOA( ? ) and starttime <= ?";
        try {
            UserSession userSession = jdbcTemplate.queryForObject(SQL, new Object[]{ip, startDateTime},
                    new RowMapper<UserSession>() {
                @Override
                public UserSession mapRow(ResultSet rs, int rowNum) throws SQLException {

                    Integer userId = rs.getInt("account");
                    LocalDateTime starttime = rs.getTimestamp("starttime").toLocalDateTime();
                    LocalDateTime stoptime = rs.getTimestamp("stoptime").toLocalDateTime();
                    Long ip = rs.getLong("long_ip");

                    return new UserSession(userId, ip, starttime, stoptime);
                }
            });
            return userSession;

        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.warn(emptyResultDataAccessException.getLocalizedMessage());
        }
        return null;
    }

}
