package lorekeep.user;

import javax.persistence.*;

@Entity
@Table
public class Session {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String sessionId;
    private long userId;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setId(long id) {

        this.id = id;
    }

    public long getId() {

        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getUserId() {
        return userId;
    }
}
