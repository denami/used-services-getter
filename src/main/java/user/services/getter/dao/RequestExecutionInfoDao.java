package user.services.getter.dao;

import user.services.getter.model.RequestExecutionInfo;

public interface RequestExecutionInfoDao {
    public RequestExecutionInfo save(RequestExecutionInfo info);
    public RequestExecutionInfo getInfoByRequestId(Integer id);
}
