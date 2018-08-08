package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.RawJDBCTemplate;
import user.services.getter.model.LogRaw;

import java.util.Collection;

@Repository
public class LogRawDaoImpl implements LogRawDao {

    @Autowired
    RawJDBCTemplate rawJDBCTemplate;

    @Override
    public Collection<LogRaw> getAllLogRaw(Integer requestId) {
        return rawJDBCTemplate.getLogs(requestId);
    }

    @Override
    public Collection<LogRaw> getLogRawRange(Integer requestId, Long ofset, Long limit) {
        return rawJDBCTemplate.getLogsRange(requestId, ofset, limit);
    }

    @Override
    public Long geLogRawCount(Integer requestId) {
        return rawJDBCTemplate.getRawCount(requestId);
    }
}
