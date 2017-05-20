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

    @Query("select u from Changes u where u.sessionId = :sessionId or u.userId = :userId and u.topicId is not null")
    List<Changes> findBySessionIdAndUserId(@Param("sessionId") String sessionId,
                                            @Param("userId") Long userId);


    Changes findBySessionIdAndUserIdAndTopicId(String sessionId, Long userId, Long topicId);

    Changes findFirstBySessionId(String sessionId);

    @Modifying
    @Transactional
    @Query("delete from Changes c where c.sessionId = :sessionId")
    void deleteBySessionId(@Param("sessionId") String sessionId);



//    @Query(value = "SELECT p FROM Changes p WHERE p.sessionId = :sessionId AND p.userId = :userId", nativeQuery = true)
//    ArrayList<Changes> find(@Param("sessionId") String sessionId, @Param("userId") Long userId);

}
