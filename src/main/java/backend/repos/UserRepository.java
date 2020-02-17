package backend.repos;


import backend.model.userModels.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {
    User findUserById(Long id);
    User findUserByEmail(String eMail);
    List<User> findAllByBirthDateIsLessThanEqualAndBirthDateGreaterThanEqual(LocalDate less, LocalDate more);
}
