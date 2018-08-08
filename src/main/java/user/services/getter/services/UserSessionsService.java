package user.services.getter.services;

import user.services.getter.model.UserSession;

import java.time.LocalDateTime;

public interface UserSessionsService {

    public UserSession getUserSession(Long ip, LocalDateTime localDateTime);

}
