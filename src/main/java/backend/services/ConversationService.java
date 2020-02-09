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
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    @PersistenceContext
    EntityManager em;

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

    @Transactional
    public ArrayList<Conversation> getAllConversationOfUser() {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        ArrayList<Conversation> all = getAllConversation();
        ArrayList<Conversation> allOfOneUser = new ArrayList<>();
        for (Conversation conversation : all) {
            if (conversation.getConvPartner().equals(loggedInUserName)) {
                allOfOneUser.add(conversation);
            }
            if (conversation.getConvStarter().equals(loggedInUserName)) {
                allOfOneUser.add(conversation);
            }
        }
        return allOfOneUser;
    }

    @Transactional
    public ArrayList<Conversation> getAllConversation() {
        List<Conversation> allConversation = em.createQuery("SELECT c FROM Conversation c").getResultList(); // átírni
        return (ArrayList<Conversation>)allConversation;
    }
}
