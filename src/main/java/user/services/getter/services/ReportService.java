package user.services.getter.services;

import java.time.LocalDateTime;

public interface ReportService {

    public void save(Integer requestId, Integer userId, LocalDateTime dateTime, Long srcIp, Long dstIp, Integer bytes);

}
