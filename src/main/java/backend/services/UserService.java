package backend.services;

import backend.dto.UserDto;
import backend.dto.UserProfileDto;
import backend.exceptions.ExistingUserException;
import backend.exceptions.NotExistingUserException;
import backend.model.User;
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
    public UserProfileDto addOptionalFields(UserProfileDto userProfileDto){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getUserProfile() == null){

            UserProfile userProfile = new UserProfile();
            loadUserProfileWithUserProfileDto(userProfile, userProfileDto);

            em.persist(userProfile);
            user.setUserProfile(userProfile);
            em.persist(user);
        }
        else{
            UserProfile userProfile = user.getUserProfile();
            loadUserProfileWithUserProfileDto(userProfile, userProfileDto);

            em.persist(userProfile);
            em.persist(user);
        }

        return userProfileDto;
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

    private void loadUserProfileWithUserProfileDto(UserProfile userProfile, UserProfileDto userProfileDto){
        userProfile.setAboutMe(userProfileDto.getAboutMe());
        userProfile.setBodyShape(userProfileDto.getBodyShape());
        userProfile.setCity(userProfileDto.getCity());
        userProfile.setEyeColor(userProfileDto.getEyeColor());
        userProfile.setHairColor(userProfileDto.getHairColor());
        userProfile.setHeight(userProfileDto.getHeight());
        userProfile.setHoroscope(userProfileDto.getHoroscope());
        userProfile.setSmoking(userProfileDto.isSmoking());
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