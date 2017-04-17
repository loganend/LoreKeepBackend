package lorekeep.note;

import lorekeep.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository  extends JpaRepository<Note, Long>{

    List<Note> findAllByTopic(Topic topic);
    Note findByTopicAndNoteId(Topic topic, Long noteId);
    Note removeByNoteId(Long noteId);
}
