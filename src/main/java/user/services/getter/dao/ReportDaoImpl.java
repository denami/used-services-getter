package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.ReportJDBCTemplate;
import user.services.getter.model.Abonent;
import user.services.getter.model.Report;
import user.services.getter.services.AbonentInfoService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Repository
public class ReportDaoImpl implements ReportDao {

    @Autowired
    ReportJDBCTemplate reportJDBCTemplate;

    @Autowired
    AbonentInfoService abonentInfoService;

    @Override
    public void save(Integer requestId
            , Integer userId
            , LocalDateTime dateTime
            , Long srcIp
            , Long dstIp
            , Long natIp
            , Integer srcPort
            , Integer dstPort
            , Integer bytes) {
        reportJDBCTemplate.save(requestId, userId, dateTime, srcIp, dstIp, natIp, srcPort, dstPort, bytes);
    }

    @Override
    public void cleanReport(Integer requestId) {
        reportJDBCTemplate.cleanReport(requestId);
    }

    @Override
    public Collection<Report> getReports(Integer requestId) {

        Collection<Report> reports = new HashSet<>();
        for (Report report: reportJDBCTemplate.getReports(requestId)) {
            Abonent abonent = abonentInfoService.getAbonentById(report.getUserId());
            report.setAbnInfo(abonent.toString());
            reports.add(report);
        }

        return reports;
    }
}
