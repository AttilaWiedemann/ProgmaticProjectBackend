package repos;


import model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository {
    User findByIdWithUser(Long Id);
}
