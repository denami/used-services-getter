package user.services.getter.model;

import java.time.LocalDateTime;

public class UserSession implements Comparable<UserSession> {

    private Integer userId;
    private Long ip;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public UserSession(Integer userId, Long ip, LocalDateTime startTime, LocalDateTime endTime) {
        this.userId = userId;
        this.ip = ip;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(UserSession o) {
        return 0;
    }

}
