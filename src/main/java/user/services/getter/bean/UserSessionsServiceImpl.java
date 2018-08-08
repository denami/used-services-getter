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

        if (userSessions.containsKey(ip) ) {
            UserSession userSession = userSessions.get(ip);
            if((userSession.getStartTime().isBefore(localDateTime) || userSession.getStartTime().equals(localDateTime))
            && (userSession.getEndTime().isAfter(localDateTime) || userSession.getStartTime().equals(localDateTime))) {
             return userSession;
            } else {
                userSession = recieveFromDB(ip,localDateTime);
                userSessions.put(userSession.getIp(), userSession);
                return userSession;
            }
        }

        return null;
    }

    private UserSession recieveFromDB(Long ip, LocalDateTime localDateTime) {
        return userSessionDao.getSession(ip, localDateTime);
    }
}
