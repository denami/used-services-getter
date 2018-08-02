package user.services.getter.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) &&
                Objects.equals(createDateTime, request.createDateTime) &&
                status == request.status &&
                Objects.equals(startDate, request.startDate) &&
                Objects.equals(endDate, request.endDate) &&
                Objects.equals(requestedIpAddress, request.requestedIpAddress) &&
                Objects.equals(requestedDomainAddress, request.requestedDomainAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDateTime, status, startDate, endDate, requestedIpAddress, requestedDomainAddress);
    }
}
