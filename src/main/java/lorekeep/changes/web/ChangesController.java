package lorekeep.changes.web;


import lorekeep.changes.Changes;
import lorekeep.changes.ChangesRepository;
import lorekeep.topic.Topic;
import lorekeep.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ChangesController {

    @Autowired
    private ChangesRepository changesRepository;

    @Autowired
    private TopicRepository topicRepository;


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
}
