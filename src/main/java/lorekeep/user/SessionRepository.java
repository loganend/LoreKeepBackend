package lorekeep.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findBySessionId(String sessionId);

    List<Session> findByUserId(Long userId);



}
