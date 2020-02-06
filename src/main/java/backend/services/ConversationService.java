package backend.services;

import backend.dto.ConversationDto;
import backend.model.Conversation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ConversationService {

    @PersistenceContext
    EntityManager em;

    @Transactional //nincs készen
    public void createConversation(ConversationDto conversationDto) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Conversation newConversation = new Conversation();
        newConversation.setConvPartner(loggedInUserName);
        newConversation.setConvStarter(conversationDto.getConvPartner());
        em.persist(newConversation);
    }




}
