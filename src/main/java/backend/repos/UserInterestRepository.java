package backend.repos;

import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepository {
    UserInterest findUserInterestById(Long id);
}
