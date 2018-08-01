package user.services.getter.services;

import user.services.getter.model.Request;
import user.services.getter.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.Collection;

public class RequestServiceImpl implements RequestService {
    @Override
    public RequestStatus getRequestStatus(Request request) {
        return null;
    }

    @Override
    public void SetRequestStatus(Request request, RequestStatus requestStatus) {

    }

    @Override
    public Integer getRequestId(Request request) {
        return null;
    }

    @Override
    public Request getRequestById(Integer id) {
        return null;
    }

    @Override
    public Request save(Request request) {
        return getRequestByCreateDataTime(request.getCreateDateTime());
    }

    @Override
    public Collection<Request> getAllRequests() {
        return null;
    }

    @Override
    public Request getRequestByCreateDataTime(LocalDateTime localDateTime) {
        return null;
    }
}
