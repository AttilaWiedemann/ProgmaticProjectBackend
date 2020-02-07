package backend.repos;

import backend.model.UserInterest;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepository {
    UserInterest findUserInterestById(Long id);

}
