package user.services.getter.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Report implements Comparable<Report> {

    private Integer requestId;
    private Integer userId;
    private LocalDateTime dateTime;
    private String srcIp;
    private String dstIp;
    private String natIp;
    private Integer bytes = 0;
    private String abnInfo;
    private Integer srcPort = 0;
    private Integer dstPort = 0;

    public Report() {
    }

    public Report(Integer requestId
            , Integer userId
            , LocalDateTime dateTime
            , String srcIp
            , String dstIp
            , String natIp
            , Integer bytes
            , Integer srcPort
            , Integer dstPort) {
        this.requestId = requestId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.srcIp = srcIp;
        this.dstIp = dstIp;
        this.natIp = natIp;
        this.bytes = bytes;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
    }

    public Integer getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(Integer srcPort) {
        this.srcPort = srcPort;
    }

    public Integer getDstPort() {
        return dstPort;
    }

    public void setDstPort(Integer dstPort) {
        this.dstPort = dstPort;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    public String getNatIp() {
        return natIp;
    }

    public void setNatIp(String natIp) {
        this.natIp = natIp;
    }

    public String getAbnInfo() {
        return abnInfo;
    }

    public void setAbnInfo(String abnInfo) {
        this.abnInfo = abnInfo;
    }

    @Override
    public int compareTo(Report o) {
        if(o.getRequestId().equals(this.requestId)){
            if (o.getUserId().equals(this.userId)){
                if (o.getDateTime().equals(this.dateTime)){
                    if(o.getDstIp().equals(this.dstIp)){
                        if (o.getSrcIp().equals(this.srcIp)) {
                            if (o.getBytes().equals(this.bytes)) {
                                if (o.getNatIp().equals(this.natIp)) {
                                    if (o.getAbnInfo().equals(this.abnInfo)){
                                        if (o.getSrcPort().equals(this.srcPort)) {
                                            if (o.getDstPort().equals(this.dstPort)) {
                                                return 0;
                                            } else return o.getDstPort().compareTo(this.dstPort);
                                        } else return o.getSrcPort().compareTo(this.srcPort);
                                    } else return o.getAbnInfo().compareTo(this.abnInfo);
                                } else return o.getNatIp().compareTo(this.natIp);
                            } else return o.getBytes().compareTo(this.bytes);
                        }else return o.getSrcIp().compareTo(this.srcIp);
                    } else return o.getDstIp().compareTo(this.dstIp);
                } else return o.getDateTime().compareTo(this.dateTime);
            } else return o.getUserId().compareTo(this.userId);
        } else return o.getRequestId().compareTo(this.requestId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(requestId, report.requestId) &&
                Objects.equals(userId, report.userId) &&
                Objects.equals(dateTime, report.dateTime) &&
                Objects.equals(srcIp, report.srcIp) &&
                Objects.equals(srcPort, report.srcPort) &&
                Objects.equals(natIp, report.natIp) &&
                Objects.equals(dstIp, report.dstIp) &&
                Objects.equals(dstPort, report.dstPort) &&
                Objects.equals(abnInfo, report.abnInfo) &&
                Objects.equals(bytes, report.bytes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, userId, dateTime, srcIp, dstIp, natIp, bytes, abnInfo, srcPort, dstPort);
    }
}
