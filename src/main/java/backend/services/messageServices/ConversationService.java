package backend.services.messageServices;

import backend.dto.messageDtos.ConversationDto;
import backend.model.messageModels.Conversation;
import backend.model.messageModels.ConversationMessage;
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
    public Conversation createConversation(ConversationDto conversationDto) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Conversation newConversation = new Conversation();

        newConversation.setConvStarter(loggedInUserName);
        newConversation.setConvPartner(conversationDto.getConvPartner());

        ConversationMessage message = new ConversationMessage();
        message.setAuthor(loggedInUserName);
        message.setPartner(conversationDto.getConvPartner());
        message.setText(conversationDto.getFirstMessage());
        message.setCreationDate(LocalDateTime.now());
        message.setConversation(newConversation);
        em.persist(message);
        //newConversation.addMessage(message); TODO miWAAAN
        em.persist(newConversation);
        return getConversation(newConversation.getId());
    }

    public String getPartnerName(Long convId) {

        Conversation conv = em.createQuery("SELECT c FROM Conversation c where c.id = :convId", Conversation.class).setParameter("convId", convId).getSingleResult();
        return conv.getConvPartner();
        //String starterName = (String)em.createQuery("SELECT c.convStarter FROM Conversation c where c.id =: convId").setParameter("convId", convId).getSingleResult();
        //ArrayList<String> names = new ArrayList<>();

    }
    public String getSartnerName(Long convId) {
        //return (String)em.createQuery("SELECT c.convStarter FROM Conversation c where c.id =: convId").setParameter("convId", convId).getSingleResult();
        Conversation conv = em.createQuery("SELECT c FROM Conversation c where c.id = :convId", Conversation.class).setParameter("convId", convId).getSingleResult();
        return conv.getConvStarter();
    }

    @Transactional
    public Conversation getConversation(Long convId) { // TODO nem szedi ki az üzeneteket
        //("SELECT c FROM Conversation c left join fetch c.conversationMessages where c.id = :convId", Conversation.class)
//TODO itt bekerült left joint fetch 10:32
        Conversation oneConversation = em.createQuery("SELECT c FROM Conversation c where c.id = :id", Conversation.class)
                .setParameter("id", convId)
                .getSingleResult();
        //System.out.println(oneConversation.getConvPartner());
        //System.out.println(oneConversation.getId());
        //System.out.println(oneConversation.getConversationMessages().get(1));
        return oneConversation;
    }

    @Transactional
    public void createMessage(Long convId, String firstMessage) { // itt
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        ConversationMessage newMessage = new ConversationMessage();
        newMessage.setAuthor(loggedInUserName);
        //String names = getNames(convId);
        if (loggedInUserName.equals(getPartnerName(convId))) {
            newMessage.setPartner(getSartnerName(convId));

        } else {
            newMessage.setPartner(getPartnerName(convId));

        }

        /*if (loggedInUserName.equals(conversation.getConvStarter())) {
            newMessage.setAuthor(loggedInUserName);
            newMessage.setPartner(conversation.getConvPartner());
        } else  if (loggedInUserName.equals(conversation.getConvPartner())) {
            newMessage.setAuthor(loggedInUserName);
            newMessage.setPartner(conversation.getConvStarter());
        }*/

        newMessage.setConversation(getConversation(convId));
        newMessage.setCreationDate(LocalDateTime.now());
        newMessage.setText(firstMessage);
        em.persist(newMessage);

    }
/*
    @Transactional
    public ArrayList<ConversationMessage> getAllMessagesOfUser(Long id) {
        ArrayList<ConversationMessage> allmess = new ArrayList<>();

    }*/

    @Transactional
    public ArrayList<Conversation> getAllConversationOfUser() {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        ArrayList<Conversation> all = getAllConversation();
        ArrayList<Conversation> allOfOneUser = new ArrayList<>();
      // ArrayList<ConversationMessage> messagesOfUser = getAllMessagesOfUser();
        for (Conversation conversation : all) {
            long convid = conversation.getId();

            if (conversation.getConvPartner().equals(loggedInUserName)) {
                allOfOneUser.add(conversation);
            } else if (conversation.getConvStarter().equals(loggedInUserName)) {
                allOfOneUser.add(conversation);
            }
        }
        return allOfOneUser;
    }


    @Transactional
    public ArrayList<Conversation> getAllConversation() {
        List<Conversation> allConversation = em.createQuery("SELECT c FROM Conversation c", Conversation.class).getResultList(); // átírni
        return (ArrayList<Conversation>)allConversation;
    }
}
