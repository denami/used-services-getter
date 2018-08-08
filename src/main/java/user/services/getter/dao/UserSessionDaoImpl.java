package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import user.services.getter.JDBCTemplate.UserSessionJDBCTemplate;
import user.services.getter.model.UserSession;

import java.time.LocalDateTime;

public class UserSessionDaoImpl implements UserSessionDao {

    @Autowired
    UserSessionJDBCTemplate userSessionJDBCTemplate;

    @Override
    public UserSession getSession(Long ip, LocalDateTime localDateTime) {
        UserSession userSession = userSessionJDBCTemplate.getUserByLogin(ip,localDateTime);
        if (userSession.getEndTime() == null) {
            userSession.setEndTime(LocalDateTime.now());
        }
        return userSession;
    }
}
