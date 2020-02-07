package backend.services;

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
public class MessageService {
    @PersistenceContext
    EntityManager em;
    ConversationService conversationService;

    @Transactional
    public void createMessage(Long convId, MessageDto messageDto) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Message message = new Message();
        Conversation conversation = conversationService.getConversation(convId);

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

    @Autowired
    public MessageService(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
}
