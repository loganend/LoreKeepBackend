package lorekeep.user.web;

public class SessionMessage {
    private String sessionId;
    private Long userId;

    public SessionMessage(String sessionid, Long userId) {
        this.sessionId = sessionid;
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setSessionId(String sessionid) {
        this.sessionId = sessionid;
    }

    public void setUserId(Long userid) {
        this.userId = userid;
    }
}
