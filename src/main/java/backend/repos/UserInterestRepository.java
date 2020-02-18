package backend.repos;

import backend.model.userModels.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInterestRepository extends JpaRepository<UserInterest,Long> {

    Optional<UserInterest> findById(Long id);

}
