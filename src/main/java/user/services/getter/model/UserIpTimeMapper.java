package user.services.getter.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import user.services.getter.services.LogRawService;
import user.services.getter.services.ReportService;
import user.services.getter.services.RequestService;
import user.services.getter.services.UserSessionsService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@Scope("prototype")
public class UserIpTimeMapper implements Runnable {

    private static final Long COUNT = 500L;

    private Request request;

    @Autowired
    UserSessionsService userSessionsService;

    @Autowired
    RequestService requestService;

    @Autowired
    LogRawService logRawService;

    @Autowired
    ReportService reportService;

    Map<UserIpDateTime,LogInfo> raws = new HashMap<>(100);

    @Override
    public void run() {

        request.setStatus(RequestStatus.BUILDING);
        requestService.save(request);

        Long offset = 0L;
        Collection<LogRaw> logRaws = logRawService.getLogRawRange(request.getId(), offset, COUNT);

        while (logRaws != null && logRaws.size() > 0) {
            for (LogRaw logRaw : logRaws) {
                Long dstIP = logRaw.getDstIp();
                Long srcIP = logRaw.getSrcIp();
                Long natIp = logRaw.getNatIp();
                Integer srcPort = logRaw.getSrcPort();
                Integer dstPort = logRaw.getDstPort();

                Integer userId = userSessionsService.getAccountId(dstIP, logRaw.getDateTime());

                if (userId != null ) {
                } else {
                    userId = userSessionsService.getAccountId(srcIP, logRaw.getDateTime());
                }

                if(userId == null) {
                    continue;
                }

                UserIpDateTime userIpDateTime = new UserIpDateTime(
                        dstIP,
                        srcIP,
                        natIp,
                        srcPort,
                        dstPort,
                        logRaw.getDateTime().truncatedTo(ChronoUnit.MINUTES));
                if (raws.containsKey(userIpDateTime)) {
                    LogInfo logInfo = raws.get(userIpDateTime);
                    logInfo.setBytes(logInfo.getBytes() + logRaw.getBytes());
                    raws.put(userIpDateTime,logInfo);
                } else {
                    LogInfo logInfo = new LogInfo(logRaw.getBytes(), userId);
                    raws.put(userIpDateTime, logInfo);
                }
            }
            offset += COUNT;
            logRaws = logRawService.getLogRawRange(request.getId(), offset, COUNT);
        }

        request.setStatus(RequestStatus.DONE);
        save(request, raws);
        requestService.save(request);

    }

    private void save(Request request, Map<UserIpDateTime, LogInfo> raws) {

        Integer requestId = request.getId();
        Set<UserIpDateTime> keys = raws.keySet();
        for (UserIpDateTime k : keys) {
            reportService.save(requestId
                    , raws.get(k).userId
                    , k.dateTime
                    , k.srcIp
                    , k.dstIp
                    , k.natIp
                    , k.getSrcPort()
                    , k.getDstPort()
                    , raws.get(k).bytes);
        }
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    private class UserIpDateTime {
        private Long dstIp;
        private Long srcIp;
        private Long natIp;
        private Integer srcPort;
        private Integer dstPort;
        private LocalDateTime dateTime;

        public UserIpDateTime(Long dstIp
                , Long srcIp
                , Long natIp
                , Integer srcPort
                , Integer dstPort, LocalDateTime dateTime) {
            this.dstIp = dstIp;
            this.srcIp = srcIp;
            this.natIp = natIp;
            this.dateTime = dateTime;
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

        public Long getDstIp() {
            return dstIp;
        }

        public void setDstIp(Long dstIp) {
            this.dstIp = dstIp;
        }

        public Long getSrcIp() {
            return srcIp;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setSrcIp(Long srcIp) {
            this.srcIp = srcIp;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Long getNatIp() {
            return natIp;
        }

        public void setNatIp(Long natIp) {
            this.natIp = natIp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserIpDateTime that = (UserIpDateTime) o;
            return Objects.equals(dstIp, that.dstIp) &&
                    Objects.equals(srcIp, that.srcIp) &&
                    Objects.equals(natIp, that.natIp) &&
                    Objects.equals(srcPort, that.srcPort) &&
                    Objects.equals(dstPort, that.dstPort) &&
                    Objects.equals(dateTime, that.dateTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dstIp, srcIp, natIp, dateTime, srcPort, dstPort);
        }
    }

    private class LogInfo {
        private Integer bytes;
        private Integer userId;

        public LogInfo(Integer bytes, Integer userId) {
            this.bytes = bytes;
            this.userId = userId;
        }

        public Integer getBytes() {
            return bytes;
        }

        public void setBytes(Integer bytes) {
            this.bytes = bytes;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LogInfo logInfo = (LogInfo) o;
            return Objects.equals(bytes, logInfo.bytes) &&
                    Objects.equals(userId, logInfo.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bytes, userId);
        }
    }
}
