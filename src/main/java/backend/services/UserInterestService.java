package backend.services;

import backend.dto.UserInterestDto;
import backend.model.User;
import backend.model.UserInterest;
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
        userInt.setBooks(userIntDto.isBookAndLiterature());
        userInt.setCulture(userIntDto.isCulture());
        userInt.setMovies(userIntDto.isMovie());
        userInt.setMusic(userIntDto.isMusic());
        userInt.setPolitics(userIntDto.isPolitics());
        userInt.setSports(userIntDto.isSport());
        userInt.setTechnology(userIntDto.isTechnology());
        userInt.setTravels(userIntDto.isTravel());
        em.persist(userInt);
        user.setUserInterest(userInt);
        em.persist(user);
        return userIntDto;
    }
}
