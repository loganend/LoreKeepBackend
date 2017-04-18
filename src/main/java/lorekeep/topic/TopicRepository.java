package lorekeep.topic;

import lorekeep.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByUser(User user);
    Topic findByTopicId(Long topicId);
    Topic findByUserAndTopicId(User user, Long topicId);

}
