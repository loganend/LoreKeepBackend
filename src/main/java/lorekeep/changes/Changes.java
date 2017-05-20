package lorekeep.changes;

import javax.persistence.*;


@Entity
@Table(name = "changes")
public class Changes {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long changeId;

    private String sessionId;
    private long userId;
    private long topicId;
    private long noteId;
    private long topicDelId;
    private long noteDelId;

    public void setTopicDelId(long topicDelId) {
        this.topicDelId = topicDelId;
    }

    public void setNoteDelId(long noteDelId) {
        this.noteDelId = noteDelId;
    }

    public long getTopicDelId() {

        return topicDelId;
    }

    public long getNoteDelId() {
        return noteDelId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSessionId() {

        return sessionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public void setSession(String session) {
        this.sessionId = session;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getChangeId() {

        return changeId;
    }

    public String getSession() {
        return sessionId;
    }

    public long getTopicId() {
        return topicId;
    }

    public long getNoteId() {
        return noteId;
    }
}
