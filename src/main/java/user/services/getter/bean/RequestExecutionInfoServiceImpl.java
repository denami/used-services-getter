package user.services.getter.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.services.getter.dao.RequestExecutionInfoDao;
import user.services.getter.model.RequestExecutionInfo;
import user.services.getter.services.RequestExecutionInfoService;

@Service
public class RequestExecutionInfoServiceImpl implements RequestExecutionInfoService {

    @Autowired
    RequestExecutionInfoDao requestExecutionInfoDao;

    @Override
    public RequestExecutionInfo getInfoByRequestId(Integer id) {
        RequestExecutionInfo info;
        info = requestExecutionInfoDao.getInfoByRequestId(id);
        if (info == null) {
            info = new RequestExecutionInfo();
            info.setRequestId(id);
        }
        return info;
    }

    @Override
    public RequestExecutionInfo save(RequestExecutionInfo info) {
        return requestExecutionInfoDao.save(info);
    }
}
