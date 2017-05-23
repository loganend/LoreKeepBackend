package lorekeep.note.web;

import lorekeep.changes.Changes;
import lorekeep.changes.ChangesRepository;
import lorekeep.note.Note;
import lorekeep.note.NoteRepository;
import lorekeep.note.web.json.JsonNoteCreate;
import lorekeep.note.web.json.JsonNoteUpdate;
import lorekeep.topic.Topic;
import lorekeep.topic.TopicRepository;
import lorekeep.user.Session;
import lorekeep.user.SessionRepository;
import lorekeep.user.UserRepository;
import lorekeep.user.web.json.ErrorMessage;
import lorekeep.user.web.json.Response;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lorekeep.user.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteContoller {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ChangesRepository changesRepository;

    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public ResponseEntity createNote(@RequestBody JsonNoteCreate noteJson, @CookieValue("sessionId") String sessionId) {

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
        if(noteJson.getImage() != null)
            note.setImage(Base64.decodeBase64(noteJson.getImage()));

//        SimpleDateFormat formatter = new SimpleDateFormat();
//        try {
//            note.setCreationDate(formatter.parse(noteJson.getCreationDate()));
//        } catch (ParseException ex) {
//
//        }
        note.setRating(0);
        note.setLastUsed(null);
        note.setChanged(null);

        note = noteRepository.save(note);



        List<Session> sessions = sessionRepository.findByUserId(topic.getUser().getUserId());


        for(int i=0; i<sessions.size(); i++){
            if(!sessions.get(i).getSessionId().equals(sessionId)){
                Changes changes = new Changes();
                changes.setUserId(topic.getUser().getUserId());
                changes.setTopicId(topic.getTopicId());
                changes.setSession(sessions.get(i).getSessionId());
                changes.setNoteId(note.getNoteId());

                changesRepository.save(changes);
            }
        }







        return ResponseEntity.ok().body(new Response("id", note.getNoteId()));
    }

    @RequestMapping(value = "/note/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllNotes(@PathVariable("id") long id) {
        Topic topic = topicRepository.findByTopicId(id);
        return ResponseEntity.ok(noteRepository.findAllByTopic(topic));
    }

    @RequestMapping(value = "note/all/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getALLNotesByUserId(@PathVariable("userId") long userId){

        User user = userRepository.findByUserId(userId);
        List<Topic> topics = topicRepository.findAllByUser(user);
        List<Note> notes = new ArrayList<Note>();

        for(int i = 0; i<topics.size(); i++){
            notes.addAll(noteRepository.findAllByTopic(topics.get(i)));
        }

        return ResponseEntity.ok(notes);
    }

    @RequestMapping(value = "/note/{noteId}/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteNote(@PathVariable("noteId") Long noteId, @PathVariable("userId") Long userId, @CookieValue("sessionId") String sessionId) {

        Note note = noteRepository.findByNoteId(noteId);

        try {
            noteRepository.delete(noteId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"no content\"}");
        }

        List<Session> sessions = sessionRepository.findByUserId(userId);

        for(int i=0; i<sessions.size(); i++){
            if(!sessions.get(i).getSessionId().equals(sessionId)){

                Changes changes = new Changes();
                changes.setUserId(userId);
                changes.setSession(sessions.get(i).getSessionId());
                changes.setTopicDelId(note.getNoteId());
                changesRepository.save(changes);
            }
        }

        return ResponseEntity.ok().body(new Response("info", "deleted"));
    }

    @RequestMapping(value = "/note", method = RequestMethod.PUT)
    public ResponseEntity updateNote(@RequestBody JsonNoteUpdate noteJson, @CookieValue("sessionId") String sessionId) {
        if (noteJson == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"error\": \"no content");
        }

        Topic topic = topicRepository.findByTopicId(Long.parseLong(noteJson.getServerTopicId()));
        if (topic == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("topic not exist")));
        }
        Note note = noteRepository.findByTopicAndNoteId(topic, Long.parseLong(noteJson.getServerNoteId()));
        if (note == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("Note not exist")));
        }
        if (noteJson.getComment() != null)
            note.setComment(noteJson.getComment());
        if (noteJson.getContent() != null)
            note.setContent(noteJson.getContent());
        if (noteJson.getUrl() != null)
            note.setUrl(noteJson.getUrl());
        if (noteJson.getImage() != null)
            note.setImage(Base64.decodeBase64(noteJson.getImage()));
        if (noteJson.getLastUsed() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat();
            try {
                note.setLastUsed(formatter.parse(noteJson.getLastUsed()));
            } catch (ParseException ex) {

            }
        }
        if (noteJson.getRating() != null)
            note.setRating(Integer.parseInt(noteJson.getRating()));
        if (noteJson.getTopicId() != null)
            note.setLastUsed(null);
        if (noteJson.getChanged() != null)
            note.setChanged(Boolean.parseBoolean(noteJson.getChanged()));

        noteRepository.save(note);



        List<Session> sessions = sessionRepository.findByUserId(topic.getUser().getUserId());

        for(int i=0; i<sessions.size(); i++){
            if(!sessions.get(i).getSessionId().equals(sessionId)){

                Changes changes = new Changes();
                changes.setUserId(topic.getUser().getUserId());
                changes.setSession(sessions.get(i).getSessionId());
                changes.setTopicId(topic.getTopicId());
                changes.setNoteId(note.getNoteId());

                changesRepository.save(changes);
            }
        }

        return ResponseEntity.ok().body(new Response("info", note.getNoteId()));
    }
}
