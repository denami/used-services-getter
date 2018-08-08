package user.services.getter.dao;

import java.time.LocalDateTime;

public interface ReportDao {

    public void save(Integer requestId, Integer userId, LocalDateTime dateTime, Long srcIp, Long dstIp, Integer bytes);

}
