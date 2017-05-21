package lorekeep.note;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lorekeep.topic.Topic;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteId;

    //    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.REMOVE);
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "topicId")
    @JsonIgnore
    private Topic topic;
    private String comment;
    @Column(columnDefinition = "text")
    private String content;
    private String url;
    private byte[] image;
    private Integer rating;
    private Date creationDate;
    private Date lastUsed;
    private Boolean changed;

    public Note() {
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    public long getNoteId() {

        return noteId;
    }

    public Topic getTopic() {
        return topic;
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

    public Boolean getChanged() {
        return changed;
    }


}
