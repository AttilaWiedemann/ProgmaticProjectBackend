package backend.services;

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

    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createUser(User user){

        if (userRepository.findUserByEmail(user.getEmail()).equals(null)){
            em.persist(user);
            return userRepository.findUserByEmail(user.getEmail()).getId();
        }
        else{
            throw new ExistingUserException(user.getName());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
