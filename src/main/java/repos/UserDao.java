package repos;


import model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User,Long> {
    User findByIdWithUser(Long id);
    User findUserByEmail(String eMail);
}
