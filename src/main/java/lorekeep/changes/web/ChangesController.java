package lorekeep.changes.web;


import lorekeep.changes.Changes;
import lorekeep.changes.ChangesRepository;
import lorekeep.topic.Topic;
import lorekeep.topic.TopicRepository;
import lorekeep.user.web.json.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
    private HttpSession httpSession;

    @RequestMapping(value = "/changes/", method = RequestMethod.GET)
    public ResponseEntity<?> changesTopic() {

        String session = httpSession.getId();
//        Long user = (Long)httpSession.getAttribute("userId");

        Changes changes = changesRepository.findFirstBySessionId(session);
        if (changes == null) {
            return ResponseEntity.ok(new Response("Response", "Nothing to sync"));
        }

        List<Topic> topics = new ArrayList<Topic>();

        List<Changes> topicsId = changesRepository.findBySessionIdAndUserId(session, changes.getUserId());

        for (int i = 0; i < topicsId.size(); i++)
            topics.add(i, topicRepository.findByTopicId(topicsId.get(i).getTopicId()));

        return ResponseEntity.ok(topics);
    }
}
