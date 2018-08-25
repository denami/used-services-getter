package user.services.getter.services;

import user.services.getter.model.Report;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ReportService {

    public void save(Integer requestId, Integer userId, LocalDateTime dateTime, Long srcIp, Long dstIp,
                     Long natIp, Integer bytes);
    public void cleanReport(Integer requestId);
    public Collection<Report> getReports(Integer requestId);

}
