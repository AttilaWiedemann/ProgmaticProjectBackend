package backend.services;

import backend.dto.UserDto;
import backend.dto.UserProfileDto;
import backend.exceptions.ExistingUserException;
import backend.model.User;
import backend.model.UserProfile;
import backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserService(UserRepository userRepository, EmailServiceImpl emailService) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto createUser(UserDto userDto){

        //NotEmpty String name, @NotEmpty String password, @Email String email, LocalDate birthDate

        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setBirthDate(userDto.getBirthDate());

        //user.setGender(userDto.getGender());
        //user.setIntrest(userDto.getIntrest());

        if (userRepository.findUserByEmail(user.getEmail()) == null) {
            em.persist(user);
            emailService.sendSimpleMessage(user.getEmail(), "WELCOME", "WELCOME");
            return userDto;
        }
        else{
            throw new ExistingUserException(user.getName());
        }
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try{
            return em.createQuery("select u from User u left join fetch u.authorities where u.email=:email ", User.class)
                    .setParameter("email", s).getSingleResult();
        } catch(NoResultException e){
            throw new UsernameNotFoundException("User not found: " + s);
        }
    }


    @Transactional
    public UserProfileDto addOptionalFields(UserProfileDto userProfileDto, Long id){

        User user = userRepository.findUserById(id);

        if (user != null){
            if (user.getUserProfile() == null){
                UserProfile userProfile = new UserProfile();

                userProfile.setAboutMe(userProfileDto.getAboutMe());
                userProfile.setBodyShape(userProfileDto.getBodyShape());
                userProfile.setCity(userProfileDto.getCity());
                userProfile.setEyeColor(userProfileDto.getEyeColor());
                userProfile.setHairColor(userProfileDto.getHairColor());
                userProfile.setHeight(userProfileDto.getHeight());
                userProfile.setHoroscope(userProfileDto.getHoroscope());
                userProfile.setSmoking(userProfileDto.isSmoking());

                em.persist(userProfile);
                user.setUserProfile(userProfile);
                em.persist(user);
            }
            else{

                UserProfile userProfile = user.getUserProfile();

                userProfile.setSmoking(userProfileDto.isSmoking());
                userProfile.setHoroscope(userProfileDto.getHoroscope());
                userProfile.setHeight(userProfileDto.getHeight());
                userProfile.setHairColor(userProfileDto.getHairColor());
                userProfile.setEyeColor(userProfileDto.getEyeColor());
                userProfile.setCity(userProfileDto.getCity());
                userProfile.setBodyShape(userProfileDto.getBodyShape());
                userProfile.setAboutMe(userProfileDto.getAboutMe());

                em.persist(userProfile);

                em.persist(user);
            }
        }
        else{
            throw new ExistingUserException(user.getName());
        }

        return userProfileDto;
    }

}
