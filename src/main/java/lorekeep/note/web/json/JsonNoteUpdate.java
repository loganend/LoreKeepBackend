package lorekeep.note.web.json;

public class JsonNoteUpdate {

    private String noteId;
    private String topicId;
    private String serverTopicId;
    private String serverNoteId;
    private String comment;
    private String content;
    private String url;
    private String image;
    private String rating;
    private String lastUsed;
    private String changed;

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getServerTopicId() {

        return serverTopicId;
    }

    public String getServerNoteId() {
        return serverNoteId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getComment() {
        return comment;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public String getRating() {
        return rating;
    }

    public String getLastUsed() {
        return lastUsed;
    }

    public String getChanged() {
        return changed;
    }
}
