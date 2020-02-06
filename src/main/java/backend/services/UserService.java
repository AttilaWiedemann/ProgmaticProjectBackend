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
        return null;
    }


    @Transactional
    public UserProfileDto addOptionalFields(UserProfileDto userProfileDto){

        if ()


        UserProfile userProfile = new UserProfile();
        /*
        userProfile.setAboutMe(userProfileDto.getAboutMe());
        userProfile.setBodyShape(userProfileDto.getBodyShape());
        userProfile.setCity(userProfileDto.getCity());
        userProfile.setEyeColor(userProfileDto.getEyeColor());
        userProfile.setHairColor(userProfileDto.getHairColor());
        userProfile.setHeight(userProfileDto.getHeight());
        userPro

        */


    }

}
