package user.services.getter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import user.services.getter.JDBCTemplate.RequestJDBCTemplate;
import user.services.getter.model.Request;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public class RequestDaoImpl implements RequestDao {

    @Autowired
    RequestJDBCTemplate requestJDBCTemplate;

    @Override
    public Collection<Request> getAllRequests() {
        return requestJDBCTemplate.getAllRequests();
    }

    @Override
    public Request getRequestById(Integer id) {
        return null;
    }

    @Override
    public Request getRequestByCreteDate(LocalDate date) {
        return null;
    }

    @Override
    public Request saveRequest(Request request) {
        return requestJDBCTemplate.save(request);
    }
}
