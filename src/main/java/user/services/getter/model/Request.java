package user.services.getter.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class Request {

    public Request() {
        createDateTime = LocalDateTime.now();
    }

    private Integer id;

    private LocalDateTime createDateTime;
    private RequestStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

    private Collection<String> requestedIpAddress;
    private Collection<String> requestedDomainAddress;

    private String requestedIpAddressComaList;
    private String requestedDomainAddressComaList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Collection<String> getRequestedIpAddress() {
        return requestedIpAddress;
    }
    public String getRequestedIpAddressComaList() {
        return String.join(",", requestedIpAddress);
    }

    public void setRequestedIpAddress(Collection<String> requestedIpAddress) {
        this.requestedIpAddress = requestedIpAddress;
        this.requestedIpAddressComaList = getRequestedIpAddressComaList();
    }

    public Collection<String> getRequestedDomainAddress() {
        return requestedDomainAddress;
    }
    public String getRequestedDomainAddressComaList() {
        return String.join(",", requestedDomainAddress);
    }

    public void setRequestedDomainAddress(Collection<String> requestedDomainAddress) {
        this.requestedDomainAddress = requestedDomainAddress;
        this.requestedDomainAddressComaList = getRequestedDomainAddressComaList();
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", createDateTime=" + createDateTime +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", requestedIpAddress=" + requestedIpAddressComaList +
                ", requestedDomainAddress=" + requestedDomainAddressComaList +
                '}';
    }


}
