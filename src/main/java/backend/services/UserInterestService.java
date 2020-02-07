package backend.services;

import backend.dto.UserInterestDto;
import backend.model.UserInterest;
import backend.repos.UserInterestRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserInterestService {
    private UserInterestRepository userInterestRepository;
    @PersistenceContext
    EntityManager em;

    public UserInterestDto creatUserIterest(UserInterestDto userIntDto) {
        UserInterest userInt = new UserInterest();
        userInt.setBookAndLiterature(userIntDto.isBookAndLiterature());
        userInt.setCulture(userIntDto.isCulture());
        userInt.setMovie(userIntDto.isMovie());
        userInt.setMusic(userIntDto.isMusic());
        userInt.setPolitics(userIntDto.isPolitics());
        userInt.setSport(userIntDto.isSport());
        userInt.setTechnology(userIntDto.isTechnology());
        userInt.setTravel(userIntDto.isTravel());
        em.persist(userInt);

        return userIntDto;
    }
}
