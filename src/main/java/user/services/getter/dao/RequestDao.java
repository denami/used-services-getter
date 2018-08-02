package user.services.getter.dao;

import user.services.getter.model.Request;

import java.time.LocalDate;
import java.util.Collection;

public interface RequestDao {
    public Collection<Request> getAllRequests();
    public Request getRequestById(Integer id);
    public Request getRequestByCreteDate(LocalDate date);
    public Request saveRequest(Request request);
}
