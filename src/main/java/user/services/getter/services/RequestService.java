package user.services.getter.services;

import user.services.getter.model.Request;
import user.services.getter.model.RequestStatus;

import java.time.LocalDateTime;
import java.util.Collection;

public interface RequestService {

    public RequestStatus getRequestStatus(Request request);
    public void SetRequestStatus(Request request, RequestStatus requestStatus);
    public Integer getRequestId(Request request);
    public Request getRequestById(Integer id);
    public Request save(Request request);
    public Collection<Request> getAllRequests();
    public Request getRequestByCreateDataTime(LocalDateTime localDateTime);


}
