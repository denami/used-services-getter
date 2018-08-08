package user.services.getter.services;

import user.services.getter.model.LogRaw;

import java.util.Collection;

public interface LogRawService {
    public Collection<LogRaw> getAllLogRaw(Integer requestId);
    public Collection<LogRaw> getLogRawRange(Integer requestId, Long offset, Long limit);
    public Long geLogRawCount(Integer requestId);
}
