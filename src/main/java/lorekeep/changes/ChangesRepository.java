package lorekeep.changes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ChangesRepository extends JpaRepository<Changes, Long> {

//    @Query ("select c from Changes c")
    //    ArrayList<Changes> findAllByCustomQueryAndStream();
    @Modifying
    @Transactional
    @Query("select u from Changes u where u.sessionId = :sessionId and u.userId = :userId and u.noteId = 0 and u.topicId > 0")
    List<Changes> findBySessionIdAndUserI(@Param("sessionId") String sessionId,
                                            @Param("userId") Long userId);
    @Modifying
    @Transactional
    @Query("select u.topicDelId from Changes u where u.sessionId = :sessionId and u.userId = :userId and u.topicDelId > 0")
    List<Long> findDelBySessionIdAndUserI(@Param("sessionId") String sessionId,
                                              @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("select u from Changes u where u.sessionId = :sessionId and u.userId = :userId and u.topicId = :topicId and u.noteId > 0")
    List<Changes> findNotesBySessionAndUser(@Param("sessionId") String sessionId,
            @Param("userId") Long userId, @Param("topicId") Long topicId);

    @Modifying
    @Transactional
    @Query("select u.topicDelId from Changes u where u.sessionId = :sessionId and u.userId = :userId and u.noteDelId > 0")
    List<Changes> findNotesDelBySessionAndUser(@Param("sessionId") String sessionId,
                                            @Param("userId") Long userId);


    Changes findBySessionIdAndUserIdAndTopicId(String sessionId, Long userId, Long topicId);

    Changes findFirstBySessionId(String sessionId);

    @Modifying
    @Transactional
    @Query("delete from Changes c where c.sessionId = :sessionId and c.topicDelId > 0")
    void deleteSessionDelTopic(@Param("sessionId") String sessionId);

    @Modifying
    @Transactional
    @Query("delete from Changes c where c.sessionId = :sessionId and c.topicId > 0 and c.noteId = 0")
    void deleteSessionCreatedTopic(@Param("sessionId") String sessionId);

    @Modifying
    @Transactional
    @Query("delete from Changes c where c.sessionId = :sessionId and c.topicId = :topicId and c.noteId > 0")
    void deleteSessionCreatedNote(@Param("sessionId") String sessionId, @Param("topicId") Long topicId);





//    @Query(value = "SELECT p FROM Changes p WHERE p.sessionId = :sessionId AND p.userId = :userId", nativeQuery = true)
//    ArrayList<Changes> find(@Param("sessionId") String sessionId, @Param("userId") Long userId);

}
