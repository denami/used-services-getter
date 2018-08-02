package user.services.getter.dao;

import user.services.getter.model.Request;
import user.services.getter.model.RequestStatus;

import java.time.LocalDate;
import java.util.Collection;

public interface RequestDao {
    public Collection<Request> getAllRequests();
    public Request getRequestById(Integer id);
    public Request getRequestByCreteDate(LocalDate date);
    public Request getRequestByStatus(RequestStatus requestStatus);
    public Request saveRequest(Request request);

}
