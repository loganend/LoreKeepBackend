package lorekeep.topic.web;

import lorekeep.user.User;

import java.util.Date;

public class JsonTopicUpdate {

    private String topicId;
    private String userId;
    private String title;
    private String image;
    private String rating;
    private String creationDate;
    private String lastUsed;
    private String color;
    private String changed;

    public String getTopicId() {
        return topicId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getRating() {
        return rating;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getLastUsed() {
        return lastUsed;
    }

    public String getColor() {
        return color;
    }

    public String getChanged() {
        return changed;
    }
}
