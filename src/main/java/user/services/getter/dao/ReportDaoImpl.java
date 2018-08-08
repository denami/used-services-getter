package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.ReportJDBCTemplate;

import java.time.LocalDateTime;

@Repository
public class ReportDaoImpl implements ReportDao {

    @Autowired
    ReportJDBCTemplate reportJDBCTemplate;

    @Override
    public void save(Integer requestId, Integer userId, LocalDateTime dateTime, Long srcIp, Long dstIp, Integer bytes) {
        reportJDBCTemplate.save(requestId, userId, dateTime, srcIp, dstIp, bytes);
    }
}
