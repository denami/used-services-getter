package user.services.getter.dao;

import user.services.getter.model.LogRaw;

import java.util.Collection;

public interface LogRawDao {
    public Collection<LogRaw> getAllLogRaw(Integer requestId);
    public Collection<LogRaw> getLogRawRange(Integer requestId, Long ofset, Long limit);
    public Long geLogRawCount(Integer requestId);
}
