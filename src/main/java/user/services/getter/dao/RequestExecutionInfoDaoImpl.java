package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.RequestExecutionInfoJDBCTemplate;
import user.services.getter.model.RequestExecutionInfo;

@Repository
public class RequestExecutionInfoDaoImpl implements RequestExecutionInfoDao {

    @Autowired
    RequestExecutionInfoJDBCTemplate requestExecutionInfoJDBCTemplate;

    @Override
    public RequestExecutionInfo save(RequestExecutionInfo info) {
        return requestExecutionInfoJDBCTemplate.save(info);
    }

    @Override
    public RequestExecutionInfo getInfoByRequestId(Integer id) {
        return requestExecutionInfoJDBCTemplate.getInfoByRequestId(id);
    }
}
