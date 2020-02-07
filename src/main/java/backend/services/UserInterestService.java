package backend.services;

import backend.repos.UserInterestRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserInterestService {
    private UserInterestRepository userInterestRepository;
    @PersistenceContext
    EntityManager em;

    public UserInterestDto creatUserIterest(UserInterestDto userInterestDto){
        UserInterest userInterest= new UserInterest;

    }
}
