package lorekeep.topic.web;

import lorekeep.topic.Topic;
import lorekeep.topic.web.json.JsonTopicCreate;
import lorekeep.topic.web.json.JsonTopicUpdate;
import lorekeep.user.User;
import lorekeep.topic.TopicRepository;
import lorekeep.user.UserRepository;
import lorekeep.user.web.json.ErrorMessage;
import lorekeep.user.web.json.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.codec.binary.Base64;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/topic", method = RequestMethod.POST)
    public ResponseEntity createTopic(@RequestBody JsonTopicCreate topicJson) {

        if (topicJson == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"error\": \"no content");
        }

        Topic topic = new Topic();
        User user = new User();
        user = userRepository.findByUserId(Long.parseLong(topicJson.getUserId()));
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("User not exist")));
        }
        topic.setUser(user);
        topic.setTitle(topicJson.getTitle());
        topic.setImage(Base64.decodeBase64(topicJson.getImage()));
        SimpleDateFormat formatter = new SimpleDateFormat();
        try {
            topic.setCreationDate(formatter.parse(topicJson.getCreationDate()));
        }catch(ParseException ex){

        }
        topic.setRating(0);
        topic.setLastUsed(null);
        topic.setColor(topicJson.getColor());
        topic.setChanged(null);

        topic = topicRepository.save(topic);

        return ResponseEntity.ok().body(new Response("id",topic.getTopicId()));
    }

    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTopics(@PathVariable("id") long id) {
        User user = userRepository.findByUserId(id);
        return ResponseEntity.ok(topicRepository.findAllByUser(user));
    }

    @RequestMapping(value = "/topic/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTopic(@PathVariable("id") long id) {

        try {
            topicRepository.delete(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"no content\"}");
        }

        return ResponseEntity.ok().body(new Response("info", "deleted"));
    }

    @RequestMapping(value = "/topic", method = RequestMethod.PUT)
    public ResponseEntity updateTopic(@RequestBody JsonTopicUpdate topicJson){
        if (topicJson == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"error\": \"no content");
        }

        Topic topic = new Topic();
        User user = userRepository.findByUserId(Long.parseLong(topicJson.getUserId()));
        if(topic == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("User not exist")));
        }
        topic = topicRepository.findByUserAndTopicId(user, Long.parseLong(topicJson.getTopicId()));
        if(topic == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response("error",new ErrorMessage("Topic not exist")));
        }
        if(topicJson.getTitle() != null)
            topic.setTitle(topicJson.getTitle());
        if(topicJson.getImage() != null)
            topic.setImage(Base64.decodeBase64(topicJson.getImage()));
        if(topicJson.getLastUsed() != null ) {
            SimpleDateFormat formatter = new SimpleDateFormat();
            try {
                topic.setLastUsed(formatter.parse(topicJson.getLastUsed()));
            } catch (ParseException ex) {

            }
        }
        if(topicJson.getRating() != null)
            topic.setRating(Integer.parseInt(topicJson.getRating()));
        if(topicJson.getTopicId() != null)
            topic.setLastUsed(null);
        if(topicJson.getColor() != null)
            topic.setColor(topicJson.getColor());
        if(topicJson.getChanged() != null)
            topic.setChanged(Boolean.parseBoolean(topicJson.getChanged()));

        topicRepository.save(topic);

        return ResponseEntity.ok().body(new Response("info", "updated"));
    }
}
