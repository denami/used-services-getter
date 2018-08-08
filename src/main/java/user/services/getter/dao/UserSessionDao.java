package user.services.getter.dao;

import user.services.getter.model.UserSession;

import java.time.LocalDateTime;

public interface UserSessionDao {

    public UserSession getSession(Long ip, LocalDateTime localDateTime);

}
