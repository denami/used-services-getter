package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.ReportJDBCTemplate;
import user.services.getter.model.Report;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public class ReportDaoImpl implements ReportDao {

    @Autowired
    ReportJDBCTemplate reportJDBCTemplate;

    @Override
    public void save(Integer requestId, Integer userId, LocalDateTime dateTime, Long srcIp, Long dstIp,
                     Long natIp, Integer bytes) {
        reportJDBCTemplate.save(requestId, userId, dateTime, srcIp, dstIp, natIp, bytes);
    }

    @Override
    public void cleanReport(Integer requestId) {
        reportJDBCTemplate.cleanReport(requestId);
    }

    @Override
    public Collection<Report> getReports(Integer requestId) {
        return reportJDBCTemplate.getReports(requestId);
    }
}
