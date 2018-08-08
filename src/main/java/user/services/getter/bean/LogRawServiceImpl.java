package user.services.getter.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import user.services.getter.JDBCTemplate.RawJDBCTemplate;
import user.services.getter.model.LogRaw;
import user.services.getter.services.LogRawService;

import java.util.Collection;

@Component
public class LogRawServiceImpl implements LogRawService {


    @Autowired
    RawJDBCTemplate rawJDBCTemplate;

    @Override
    public Collection<LogRaw> getAllLogRaw(Integer requestId) {
        return rawJDBCTemplate.getLogs(requestId);
    }

    @Override
    public Collection<LogRaw> getLogRawRange(Integer requestId, Long offset, Long limit) {
        return rawJDBCTemplate.getLogsRange(requestId, offset, limit);
    }

    @Override
    public Long geLogRawCount(Integer requestId) {
        return rawJDBCTemplate.getRawCount(requestId);
    }
}
