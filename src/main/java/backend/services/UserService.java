package backend.services;

import backend.dto.UserDto;
import backend.dto.UserProfileDto;
import backend.dto.UserProfileWithVisibleFields;
import backend.enums.*;
import backend.exceptions.ExistingUserException;
import backend.exceptions.NotExistingUserException;
import backend.model.User;
import backend.model.UserInterest;
import backend.model.UserProfile;
import backend.model.VerificationToken;
import backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private EmailServiceImpl emailService;
    private TokenService tokenService;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserService(UserRepository userRepository, EmailServiceImpl emailService, TokenService tokenService) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        //TODO
        try {
            User user = em.createQuery("select u from User u left join fetch u.authorities where u.email=:email ", User.class)
                    .setParameter("email", mail).getSingleResult();
            if(user.isEnabled() == true) {
                return user;
            }
            else {
                throw new UsernameNotFoundException("User was not found: " + mail);
            }
        } catch (NoResultException e) {
            //logger.debug("User was not found: {}", username);
            throw new UsernameNotFoundException("User was not found: " + mail);
        }
    }


    @Transactional
    public User getUser(Long userId){
        User user = userRepository.findUserById(userId);
        if (user != null){
            return user;
        }
        else {
            throw new NotExistingUserException("Not existing User.");
        }
    }

    @Transactional
    public User createUser(UserDto userDto){

        if (userRepository.findUserByEmail(userDto.getEmail()) == null) {
            User user = loadUserWithUserDto(userDto);
            em.persist(user);
            return user;
        }
        else{
            throw new ExistingUserException(String.format("User already exist with %s email address."
                    , userDto.getEmail()));
        }
    }

    @Transactional
    public UserProfileWithVisibleFields addOptionalFields(UserProfileWithVisibleFields updatedProfile){

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = currentUser.getId();
        User user = em.find(User.class, id);

        if (user.getUserProfile() == null || user.getUserInterest() == null){
            UserProfile userProfile = new UserProfile();
            UserInterest userInterest = new UserInterest();
            //UserProfile beállítása
            generateUserProfileForUser(userProfile, updatedProfile);
            //UserInterest beállítása
            generateUserInterestForUser(userInterest, updatedProfile);
            //perzisztálása
            user.setUserProfile(userProfile);
            user.setUserInterest(userInterest);
            em.persist(userInterest);
            em.persist(userProfile);
            em.persist(user);
        }
        else{

            UserProfile userProfile = em.find(UserProfile.class, user.getUserProfile().getId());
            UserInterest userInterest = em.find(UserInterest.class, user.getUserInterest().getId());
            //UserProfile updatelés
            generateUserProfileForUser(userProfile, updatedProfile);
            //UserInterest beállítása
            generateUserInterestForUser(userInterest, updatedProfile);
            em.persist(userInterest);
            em.persist(userProfile);
        }
        return updatedProfile;
    }

    @Transactional
    public User getUserByEmail(String eMail){
        return userRepository.findUserByEmail(eMail);
    }

    @Transactional
    public VerificationToken getVerificationToken(String token) {
        return tokenService.getTokenByToken(token);
    }

    @Transactional
    public void saveRegisteredUser(User user) {
        user.setEnabled(true);
        em.persist(user);
    }

    private void generateUserProfileForUser (UserProfile userProfile, UserProfileWithVisibleFields updatedProfile){
        userProfile.setAboutMe(updatedProfile.getAboutMe());
        userProfile.setCity(updatedProfile.getCity());
        userProfile.setBodyShape(BodyShape.valueOf(updatedProfile.getBodyShape()));
        userProfile.setEyeColor(EyeColor.valueOf(updatedProfile.getEyeColor()));
        userProfile.setHairColor(HairColor.valueOf(updatedProfile.getHairColor()));
        userProfile.setHoroscope(Horoscope.valueOf(updatedProfile.getHoroscopeEnum()));
        userProfile.setGender(Gender.valueOf(updatedProfile.getGender()));
        userProfile.setSmoking(updatedProfile.isSmoking());
    }

    private void generateUserInterestForUser(UserInterest userInterest, UserProfileWithVisibleFields updatedProfile){
        userInterest.setMovies(updatedProfile.isLikesMovies());
        userInterest.setSports(updatedProfile.isLikesSports());
        userInterest.setMusic(updatedProfile.isLikesMusic());
        userInterest.setBooks(updatedProfile.isLikesBooks());
        userInterest.setCulture(updatedProfile.isLikesCulture());
        userInterest.setTravels(updatedProfile.isLikesTravels());
        userInterest.setTechnology(updatedProfile.isLikesTechnology());
        userInterest.setPolitics(updatedProfile.isLikesPolitics());
        userInterest.setMinAge(updatedProfile.getMinAge());
        userInterest.setMaxAge(updatedProfile.getMaxAge());
        userInterest.setInterest(Interest.valueOf(updatedProfile.getInterest()));
    }

    private User loadUserWithUserDto(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setBirthDate(userDto.getBirthDate());
        return user;
    }
}