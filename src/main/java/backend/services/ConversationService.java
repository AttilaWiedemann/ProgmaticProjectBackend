package backend.services;

import backend.dto.ConversationDto;
import backend.dto.MessageDto;
import backend.model.Conversation;
import backend.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

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
        createMessage(newConversation.getId(), messageDto);

        return newConversation.getId();
    }

    @Transactional
    public Conversation getConversation(Long convId) {
        Conversation oneConversation = em.find(Conversation.class, convId);
        return oneConversation;
    }

    @Transactional
    public void createMessage(Long convId, MessageDto messageDto) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Message message = new Message();
        Conversation conversation = getConversation(convId);

        if (loggedInUserName.equals(conversation.getConvPartner())) {
            message.setAuthor(conversation.getConvPartner());
            message.setPartner(conversation.getConvStarter());
        }
        if (loggedInUserName.equals(conversation.getConvStarter())) {
            message.setAuthor(conversation.getConvStarter());
            message.setPartner(conversation.getConvPartner());
        }

        message.setConversation(em.find(Conversation.class, convId));
        message.setCreationDate(LocalDateTime.now());
        message.setText(messageDto.getText());
        em.persist(message);

    }



}
