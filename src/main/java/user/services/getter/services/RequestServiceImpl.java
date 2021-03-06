package user.services.getter.services;

import org.springframework.beans.factory.annotation.Autowired;
import user.services.getter.dao.RequestDao;
import user.services.getter.model.Request;
import user.services.getter.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.Collection;

public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestDao requestDao;

    @Override
    public void SetRequestStatus(Request request, RequestStatus requestStatus) {

    }

    @Override
    public Integer getRequestId(Request request) {
        return null;
    }

    @Override
    public Request getRequestById(Integer id) {
        return requestDao.getRequestById(id);
    }

    @Override
    public Request save(Request request) {
        requestDao.saveRequest(request);
        return getRequestByCreateDataTime(request.getCreateDateTime());
    }

    @Override
    public Collection<Request> getAllRequests() {
        return requestDao.getAllRequests();
    }

    @Override
    public Request getRequestByCreateDataTime(LocalDateTime localDateTime) {
        return null;
    }

    @Override
    public Collection<Request> getAllRequestsByStatus(RequestStatus requestStatus) {
        return null;
    }

    @Override
    public Request getRequestByStatus(RequestStatus requestStatus) {
        return requestDao.getRequestByStatus(requestStatus);
    }

}
