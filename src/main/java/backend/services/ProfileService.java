package backend.services;

import backend.dto.ConversationDto;
import backend.dto.MessageDto;
import backend.model.Conversation;
import backend.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ProfileService {

    @PersistenceContext
    EntityManager em;

    /*@Transactional
    public UserDto2 getProfileInfos() {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto2 userdto = new UserDto2;
        User user = em.find(User.class, loggedInUserName);
        userdto.
*/

}
