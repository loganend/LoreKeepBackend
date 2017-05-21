package lorekeep.topic;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lorekeep.note.Note;
import lorekeep.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long topicId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"username", "email", "phoneNumber"})
    private User user;
    private String title;
    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
    private List<Note> notes;
    private byte[] image;
    private Integer rating;
    private Date creationDate;
    private Date lastUsed;
    private String color;
    private Boolean changed;

    public Topic() {
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Note> getNotes() {

        return notes;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    public long getTopicId() {

        return topicId;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImage() {
        return image;
    }

    public Integer getRating() {
        return rating;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public String getColor() {
        return color;
    }

    public Boolean getChanged() {
        return changed;
    }
}

