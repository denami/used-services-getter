package user.services.getter.services;

import user.services.getter.model.RequestExecutionInfo;

public interface RequestExecutionInfoService {
    public RequestExecutionInfo getInfoByRequestId(Integer id);
    public RequestExecutionInfo save(RequestExecutionInfo info);
}
