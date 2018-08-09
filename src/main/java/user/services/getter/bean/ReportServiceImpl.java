package user.services.getter.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import user.services.getter.dao.ReportDao;
import user.services.getter.model.Report;
import user.services.getter.services.ReportService;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportDao reportDao;

    @Override
    public void save(Integer requestId, Integer userId, LocalDateTime dateTime, Long srcIp, Long dstIp, Integer bytes) {
        reportDao.save(requestId, userId, dateTime, srcIp, dstIp, bytes);
    }

    @Override
    public void cleanReport(Integer requestId) {
        reportDao.cleanReport(requestId);
    }

    @Override
    public Report getReports(Integer requestId) {
        return null;
    }

//    @Override
//    public Collection<Report> getReports(Integer requestId) {
//        return reportDao.getReports(requestId);
//    }
}
