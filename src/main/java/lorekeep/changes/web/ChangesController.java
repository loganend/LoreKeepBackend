package lorekeep.changes.web;


import lorekeep.changes.Changes;
import lorekeep.changes.ChangesRepository;
import lorekeep.note.Note;
import lorekeep.note.NoteRepository;
import lorekeep.topic.Topic;
import lorekeep.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ChangesController {

    @Autowired
    private ChangesRepository changesRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private NoteRepository noteRepository;


    @RequestMapping(value = "/changes", method = RequestMethod.GET)
    public ResponseEntity<?> changesTopic(@CookieValue("sessionId") String cookies) {


        List<Topic> topics = new ArrayList<Topic>();

        Changes changes = changesRepository.findFirstBySessionId(cookies);
        if (changes == null) {
            return ResponseEntity.ok(topics);
        }


        List<Changes> topicsId = changesRepository.findBySessionIdAndUserI(cookies, changes.getUserId());

        for (int i = 0; i < topicsId.size(); i++) {
            topics.add(i, topicRepository.findByTopicId(topicsId.get(i).getTopicId()));
        }

        if(!topicsId.isEmpty()) {
            try {
                changesRepository.deleteSessionCreatedTopic(cookies);
            } catch (Exception e) {
            }
        }

        return ResponseEntity.ok(topics);
    }

    @RequestMapping(value = "/changes/delete/topic", method = RequestMethod.GET)
    public ResponseEntity<?> changesDelTopic(@CookieValue("sessionId") String cookies) {


        Changes changes = changesRepository.findFirstBySessionId(cookies);
        if (changes == null) {
            return ResponseEntity.ok(null);
        }

        List<Long> topicsDelId = changesRepository.findDelBySessionIdAndUserI(cookies, changes.getUserId());

        if(!topicsDelId.isEmpty()) {
            try {
                changesRepository.deleteSessionDelTopic(cookies);
            } catch (Exception e) {

            }
        }

        return ResponseEntity.ok().body(topicsDelId);
    }


    @RequestMapping(value = "/changes/note/{topicId}", method = RequestMethod.GET)
    public ResponseEntity<?> changesNote(@PathVariable("topicId") long topicId, @CookieValue("sessionId") String sessionId) {

        List<Note> notes = new ArrayList<Note>();

        Changes changes = changesRepository.findFirstBySessionId(sessionId);
        if (changes == null) {
            return ResponseEntity.ok(notes);
        }

        List<Changes> notesId = changesRepository.findNotesBySessionAndUser(sessionId, changes.getUserId(), topicId);


        for (int i = 0; i < notesId.size(); i++) {
            notes.add(i, noteRepository.findByNoteId(notesId.get(i).getNoteId()));
        }

        if(!notesId.isEmpty()) {
            try {
                changesRepository.deleteSessionCreatedNote(sessionId, topicId);
            } catch (Exception e) {
            }
        }

        return ResponseEntity.ok(notes);
    }



}
