package lorekeep.note.web.json;

public class JsonNoteCreate {

    private String topicId;
    private String comment;
    private String content;
    private String url;
    private String image;
    private String creationDate;

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

    public String getCreationDate() {
        return creationDate;
    }
}
