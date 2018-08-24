package user.services.getter.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class LogRaw implements Comparable<LogRaw> {
    private LocalDateTime dateTime;
    private Long srcIp;
    private Integer srcPort;
    private Long dstIp;
    private Long natIp;
    private Integer dstPort;
    private Integer bytes;

    public LogRaw() {

    }

    public LogRaw(LocalDateTime dateTime, Long srcIp, Integer srcPort, Long dstIp, Integer dstPort,
                  Long natIp, Integer bytes) {
        this.dateTime = dateTime;
        this.srcIp = srcIp;
        this.srcPort = srcPort;
        this.dstIp = dstIp;
        this.dstPort = dstPort;
        this.bytes = bytes;
        this.natIp = natIp;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getSrcIp() {
        return srcIp;
    }

    public Integer getSrcPort() {
        return srcPort;
    }

    public Long getDstIp() {
        return dstIp;
    }

    public Integer getDstPort() {
        return dstPort;
    }

    public Integer getBytes() {
        return bytes;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setSrcIp(Long srcIp) {
        this.srcIp = srcIp;
    }

    public void setSrcPort(Integer srcPort) {
        this.srcPort = srcPort;
    }

    public void setDstIp(Long dstIp) {
        this.dstIp = dstIp;
    }

    public Long getNatIp() {
        return natIp;
    }

    public void setNatIp(Long natIp) {
        this.natIp = natIp;
    }

    public void setDstPort(Integer dstPort) {
        this.dstPort = dstPort;
    }

    public void setBytes(Integer bytes) {
        this.bytes = bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogRaw logRaw = (LogRaw) o;
        return Objects.equals(dateTime, logRaw.dateTime) &&
                Objects.equals(srcIp, logRaw.srcIp) &&
                Objects.equals(srcPort, logRaw.srcPort) &&
                Objects.equals(dstIp, logRaw.dstIp) &&
                Objects.equals(natIp, logRaw.natIp) &&
                Objects.equals(dstPort, logRaw.dstPort) &&
                Objects.equals(bytes, logRaw.bytes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, srcIp, srcPort, dstIp, dstPort, natIp, bytes);
    }

    @Override
    public int compareTo(LogRaw o) {
        if (o.dateTime.equals(this.dateTime)) {
            if (o.srcIp.equals(this.srcIp)) {
                if (o.dstIp.equals(this.dstIp)) {
                    if (o.dstPort.equals(this.dstPort)) {
                        if (o.srcPort.equals(this.srcPort)) {
                            if (o.bytes.equals(this.bytes)) {
                                if (o.natIp.equals(this.natIp)) {
                                    return 0;
                                } else return o.natIp.compareTo(this.natIp);
                            } else return o.bytes.compareTo(this.bytes);
                        } else return o.srcPort.compareTo(this.srcPort);
                    } else return o.dstPort.compareTo(this.dstPort);
                } else return o.dstIp.compareTo(this.dstIp);
            } else return o.srcIp.compareTo(this.srcIp);
        } else return o.dateTime.compareTo(this.dateTime);
    }
}
