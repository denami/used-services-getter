package user.services.getter.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.services.getter.dao.UserSessionDao;
import user.services.getter.model.UserSession;
import user.services.getter.services.UserSessionsService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserSessionsServiceImpl implements UserSessionsService {

    @Autowired
    UserSessionDao userSessionDao;

    private Map<Long, UserSession> userSessions = new HashMap<>(256);

    @Override
    public UserSession getUserSession(Long ip, LocalDateTime localDateTime) {

        if (userSessions.containsKey(ip)) {
            UserSession userSession = userSessions.get(ip);
            if ((userSession.getStartTime().isBefore(localDateTime)
                    || userSession.getStartTime().equals(localDateTime))
                    && (userSession.getEndTime().isAfter(localDateTime)
                    || userSession.getStartTime().equals(localDateTime))) {
                return userSession;
            } else {
                userSession = updateFromDB(ip, localDateTime);
                return userSession;
            }
        } else {
            UserSession userSession = updateFromDB(ip, localDateTime);
            return userSession;
        }
    }

    @Override
    public Integer getAccountId(Long ip, LocalDateTime localDateTime) {
        return getUserSession(ip, localDateTime).getUserId();
    }

    private UserSession updateFromDB(Long ip, LocalDateTime localDateTime) {
        UserSession session = userSessionDao.getSession(ip, localDateTime);
        if (session != null) {
            userSessions.put(session.getIp(),session);
            return session;
        }
        return null;
    }
}
