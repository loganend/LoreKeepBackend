package lorekeep.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(long userId);

    User findByUsername(String username);

    User findByEmail(String email);

    User removeByUserId(long userId);

    User findByUsernameAndPassword(String username, String password);

}
