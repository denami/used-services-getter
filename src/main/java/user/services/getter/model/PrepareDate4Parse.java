package user.services.getter.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import user.services.getter.services.FileDateTimeMapperService;
import user.services.getter.services.RequestService;

import java.time.LocalDate;
import java.util.Collection;

@Component
@Scope("prototype")
public class PrepareDate4Parse implements Runnable {

    @Autowired
    RequestService requestService;

    @Autowired
    FileDateTimeMapperService fileDateTimeMapperService;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    private Integer requestId;

    @Override
    public void run() {
        Request request = requestService.getRequestById(requestId);
        request.setStatus(RequestStatus.PREPARATION);
        requestService.save(request);
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        Collection<String> files = fileDateTimeMapperService.getFilesForDateRange(startDate, endDate);
    }
}
