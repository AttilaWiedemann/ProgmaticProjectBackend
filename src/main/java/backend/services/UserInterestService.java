package backend.services;

import backend.dto.UserInterestDto;
import backend.model.User;
import backend.model.UserInterest;
import backend.repos.UserInterestRepository;
import backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserInterestService {
    private UserRepository userRepository;
    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserInterestService( UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public UserInterestDto creatUserIterest(UserInterestDto userIntDto, Long id) {

        User user =  userRepository.findUserById(id);

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
        user.setUserInterest(userInt);
        em.persist(user);
        return userIntDto;
    }
}
