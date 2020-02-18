package backend.services.messageServices;

import backend.dto.messageDtos.ConversationDto;
import backend.exceptions.ExistingConversationException;
import backend.exceptions.NotExistingConversationException;
import backend.model.messageModels.Conversation;
import backend.model.messageModels.ConversationMessage;
import backend.repos.ConversationRepository;
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

    private ConversationRepository conversationRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Conversation createConversation(ConversationDto conversationDto) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Conversation ifTheconversationExistThisIsIt = conversationRepository.findConversationByConvPartnerAndConvStarter(loggedInUserName, conversationDto.getConvPartner());
        if (ifTheconversationExistThisIsIt == null) {
            ifTheconversationExistThisIsIt = conversationRepository.findConversationByConvPartnerAndConvStarter(conversationDto.getConvPartner(), loggedInUserName);
        }

        if (ifTheconversationExistThisIsIt == null) {
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
            //newConversation.addMessage(message);
            em.persist(newConversation);
            return getConversation(newConversation.getId());
        } else {
            throw new ExistingConversationException("Conversation already exist with partner");
            //return getConversation(ifTheconversationExistThisIsIt.getId());
        }
    }


    public String getPartnerName(Long convId) {

        Conversation conv = em.createQuery("SELECT c FROM Conversation c where c.id = :convId", Conversation.class).setParameter("convId", convId).getSingleResult();
        return conv.getConvPartner();

    }

    public String getSartnerName(Long convId) {
        Conversation conv = em.createQuery("SELECT c FROM Conversation c where c.id = :convId", Conversation.class).setParameter("convId", convId).getSingleResult();
        return conv.getConvStarter();
    }

    @Transactional
    public Conversation getConversation(Long convId) {
        Conversation oneConversation = em.createQuery("SELECT c FROM Conversation c where c.id = :id", Conversation.class)
                .setParameter("id", convId)
                .getSingleResult();

        return oneConversation;
    }

    @Transactional
    public void createMessage(Long convId, String firstMessage) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        ConversationMessage newMessage = new ConversationMessage();
        newMessage.setAuthor(loggedInUserName);

        if (loggedInUserName.equals(getPartnerName(convId))) {
            newMessage.setPartner(getSartnerName(convId));
        } else {
            newMessage.setPartner(getPartnerName(convId));
        }

        newMessage.setConversation(getConversation(convId));
        newMessage.setCreationDate(LocalDateTime.now());
        newMessage.setText(firstMessage);
        em.persist(newMessage);

    }

    @Transactional
    public ArrayList<Conversation> getAllConversationOfUser() {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        ArrayList<Conversation> all = getAllConversation();
        ArrayList<Conversation> allOfOneUser = new ArrayList<>();

        for (Conversation conversation : all) {
            long convid = conversation.getId();

            if (conversation.getConvPartner().equals(loggedInUserName)) {
                allOfOneUser.add(conversation);
            } else if (conversation.getConvStarter().equals(loggedInUserName)) {
                allOfOneUser.add(conversation);
            }
        }
        if (allOfOneUser.size() < 1) {
            throw new NotExistingConversationException("There is no conversations for you yet.");
        } else {
            return allOfOneUser;
        }
    }


    @Transactional
    public ArrayList<Conversation> getAllConversation() {
        List<Conversation> allConversation = em.createQuery("SELECT c FROM Conversation c", Conversation.class).getResultList(); // átírni
        if (allConversation.size() < 1) {
            throw new NotExistingConversationException("There is no conversations at all yet.");
        } else {
            return (ArrayList<Conversation>) allConversation;
        }
    }

    @Transactional
    public Conversation conversationByParticipants(String userOne, String userTwo) {
        Conversation conversation = em.createQuery("SELECT c FROM Conversation c where c.convStarter = :convStarter and c.convPartner =:convPartner", Conversation.class)
                .setParameter("convStarter", userOne)
                .setParameter("convPartner", userTwo)
                .getSingleResult();
        if (conversation == null) {
            conversation = em.createQuery("SELECT c FROM Conversation c where c.convStarter = :convStarter and c.convPartner =:convPartner", Conversation.class)
                    .setParameter("convStarter", userTwo)
                    .setParameter("convPartner", userOne)
                    .getSingleResult();
        }
        //if conversation
        return conversation;
    }

    @Transactional
    public boolean doesConversationAlreadyExist(String loggedInUserName, String convPartner) {
        Conversation conversation = em.createQuery("SELECT c FROM Conversation c where c.convStarter = :convStarter and c.convPartner =:convPartner", Conversation.class)
                .setParameter("convStarter", loggedInUserName)
                .setParameter("convPartner", convPartner)
                .getSingleResult();
        if (conversation != null) {
            return true;
        } else {
            return false;
        }
    }
}
