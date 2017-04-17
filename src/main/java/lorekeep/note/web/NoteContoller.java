package lorekeep.note.web;

import lorekeep.note.Note;
import lorekeep.note.NoteRepository;
import lorekeep.topic.Topic;
import lorekeep.topic.TopicRepository;
import lorekeep.user.web.ErrorMessage;
import lorekeep.user.web.Response;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api")
public class NoteContoller {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private NoteRepository noteRepository;

    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public ResponseEntity createNote(@RequestBody JsonNoteCreate noteJson) {

        if (noteJson == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"error\": \"no content");
        }

        Topic topic = new Topic();
        Note note = new Note();
        topic = topicRepository.findByTopicId(Long.parseLong(noteJson.getTopicId()));
        if (topic == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("topic not exist")));
        }
        note.setTopic(topic);
        note.setComment(noteJson.getComment());
        note.setContent(noteJson.getContent());
        note.setUrl(noteJson.getUrl());
        note.setImage(Base64.decodeBase64(noteJson.getImage()));
        SimpleDateFormat formatter = new SimpleDateFormat();
        try {
            note.setCreationDate(formatter.parse(noteJson.getCreationDate()));
        } catch (ParseException ex) {

        }
        note.setRating(0);
        note.setLastUsed(null);
        note.setChanged(null);

        note = noteRepository.save(note);

        return ResponseEntity.ok().body("\"id: " + note.getNoteId());
    }

    @RequestMapping(value = "/note/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllNotes(@PathVariable("id") long id) {
        Topic topic = topicRepository.findByTopicId(id);
        return ResponseEntity.ok(noteRepository.findAllByTopic(topic));
    }

    @RequestMapping(value = "/note/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteNote(@PathVariable("id") long id) {

        try {
            noteRepository.removeByNoteId(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"no content\"}");
        }

        return ResponseEntity.ok().body("deleted");
    }

    @RequestMapping(value = "/note", method = RequestMethod.PUT)
    public ResponseEntity updateNote(@RequestBody JsonNoteUpdate noteJson) {
        if (noteJson == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"error\": \"no content");
        }

        Topic topic = topicRepository.findByTopicId(Long.parseLong(noteJson.getTopicId()));
        if (topic == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("topic not exist")));
        }
        Note note = noteRepository.findByTopicAndNoteId(topic, Long.parseLong(noteJson.getNoteId()));
        if (note == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("Note not exist")));
        }
        if (noteJson.getComment() != null)
            note.setComment(noteJson.getComment());
        else if (noteJson.getContent() != null)
            note.setContent(noteJson.getContent());
        else if (noteJson.getUrl() != null)
            note.setUrl(noteJson.getUrl());
        else if (noteJson.getImage() != null)
            note.setImage(Base64.decodeBase64(noteJson.getImage()));
        else if (noteJson.getLastUsed() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat();
            try {
                note.setLastUsed(formatter.parse(noteJson.getLastUsed()));
            } catch (ParseException ex) {

            }
        } else if (noteJson.getRating() != null)
            note.setRating(Integer.parseInt(noteJson.getRating()));
        else if (noteJson.getTopicId() != null)
            note.setLastUsed(null);
        else if (noteJson.getChanged() != null)
            note.setChanged(Boolean.parseBoolean(noteJson.getChanged()));

        noteRepository.save(note);

        return ResponseEntity.ok().body(new Response("info", "updated"));
    }
}
