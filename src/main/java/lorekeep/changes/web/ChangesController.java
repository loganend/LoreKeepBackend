package lorekeep.changes.web;


import lorekeep.changes.Changes;
import lorekeep.changes.ChangesRepository;
import lorekeep.topic.Topic;
import lorekeep.topic.TopicRepository;
import lorekeep.user.SessionRepository;
import lorekeep.user.web.json.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
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

    @Autowired
    private SessionRepository sessionRepository;

    @RequestMapping(value = "/changes", method = RequestMethod.GET)
    public ResponseEntity<?> changesTopic(@CookieValue("sessionId") String cookies) {

//        HttpSession sess = request.getSession();
//        boolean isSessionNew = sess.isNew();
//        String sessionId = sess.getId();
//        String cookieFromRequestHeader = request.getHeader("cookie");

//        String session = (String)httpSession.getAttribute("userId");
//        Long user = (Long)httpSession.getAttribute("userId");

        List<Topic> topics = new ArrayList<Topic>();

        Changes changes = changesRepository.findFirstBySessionId(cookies);
        if (changes == null) {
            return ResponseEntity.ok(topics);
        }


        List<Changes> topicsId = changesRepository.findBySessionIdAndUserId(cookies, changes.getUserId());

        for (int i = 0; i < topicsId.size(); i++) {
            topics.add(i, topicRepository.findByTopicId(topicsId.get(i).getTopicId()));
        }

        try {
            changesRepository.deleteBySessionId(cookies);
        }catch(Exception e){

        }

        return ResponseEntity.ok(topics);
    }
}
