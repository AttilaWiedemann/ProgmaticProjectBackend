package backend.services;

import backend.dto.ConversationDto;
import backend.dto.MessageDto;
import backend.model.Conversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ConversationService {

    @PersistenceContext
    EntityManager em;

    MessageService messageService;

    @Autowired
    public ConversationService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Transactional
    public Long createConversation(ConversationDto conversationDto, MessageDto messageDto) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Conversation newConversation = new Conversation();

        newConversation.setConvPartner(loggedInUserName);
        newConversation.setConvStarter(conversationDto.getConvPartner());

        em.persist(newConversation);
        messageService.createMessage(newConversation.getId(), messageDto);

        return newConversation.getId();
    }

    @Transactional
    public Conversation getConversation(Long convId) {
        Conversation oneConversation = em.find(Conversation.class, convId);
        return oneConversation;
    }


}
