package backend.services;

import backend.dto.UserDto;
import backend.exceptions.ExistingUserException;
import backend.model.User;
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
    public Long createUser(UserDto userDto){


        //NotEmpty String name, @NotEmpty String password, @Email String email, LocalDate birthDate

        User user = new User();

        user.setName("Bala");
        user.setPassword("alap");
        user.setEmail(userDto.getEmail());
        user.setBirthDate(userDto.getBirthDate());

        //user.setGender(userDto.getGender());
        //user.setIntrest(userDto.getIntrest());


        //if (userRepository.findUserByEmail(user.getEmail()).equals(null)){
            em.persist(user);
            emailService.sendSimpleMessage("dosaistvan158@gmail.com", "WELCOME", "WELCOME");
            return userRepository.findUserByEmail(user.getEmail()).getId();
        /*}
        else{
            throw new ExistingUserException(user.getName());
        }*/
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
