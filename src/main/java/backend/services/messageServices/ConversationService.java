package backend.services.messageServices;

import backend.controllers.UserController;
import backend.dto.messageDtos.ConversationDto;
import backend.exceptions.ExistingConversationException;
import backend.exceptions.NotExistingConversationException;
import backend.model.messageModels.Conversation;
import backend.model.messageModels.ConversationMessage;
import backend.model.userModels.User;
import backend.repos.ConversationRepository;
import backend.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @PersistenceContext
    EntityManager em;


    //creates new conversation, with convdto (adressee, first message)
    //checks if conversation already exist between logged in user and the adressee
    @Transactional
    public Conversation createConversation(ConversationDto conversationDto) {
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("User trying to create a new conversation");

        if (isPartnerExist(conversationDto.getConvPartner())) {
            logger.info("Partner user found, creating conversation");
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
                em.persist(newConversation);
                logger.info("User created a new conversation with partner");

                return getConversation(newConversation.getId());
            } else {
                logger.info("User didnt create a new conversation because that already exists with that partner");
                throw new ExistingConversationException("Conversation already exists with partner");
            }

        } else {
            logger.info("Not existing user to create conversation error");
            throw new NotExistingConversationException("Not existing user to create conversation");
        }
       /*
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
            em.persist(newConversation);
            logger.info("User created a new conversation with partner");

            return getConversation(newConversation.getId());
        } else {
            logger.info("User didnt create a new conversation because already exist with that partner");
            throw new ExistingConversationException("Conversation already exist with partner");
        }*/
    }


    //by id of a conversation, gets the name of the adressee in that conversation
    @Transactional
    public String getPartnerName(Long convId) {

        Conversation conv = conversationRepository.findConversationById(convId);
        //Conversation conv = em.createQuery("SELECT c FROM Conversation c where c.id = :convId", Conversation.class).setParameter("convId", convId).getSingleResult();
        return conv.getConvPartner();

    }

    //by id of a conversation, gets the name of the starter of that conversation
    @Transactional
    public String getSartnerName(Long convId) {
        Conversation conv = conversationRepository.findConversationById(convId);
        //Conversation conv = em.createQuery("SELECT c FROM Conversation c where c.id = :convId", Conversation.class).setParameter("convId", convId).getSingleResult();
        return conv.getConvStarter();
    }

    //by id, gets the conversation
    @Transactional
    public Conversation getConversation(Long convId) {
        Conversation oneConversation = conversationRepository.findConversationById(convId);
        /*Conversation oneConversation = em.createQuery("SELECT c FROM Conversation c where c.id = :id", Conversation.class)
                .setParameter("id", convId)
                .getSingleResult();
*/
        return oneConversation;
    }

    //creating a new message (if conversation exists)
    //with input of an id (conversation) and the text
    //throws error if no conversations with whom user wants to send the message
    @Transactional
    public void createMessage(Long convId, String firstMessage) {
        if (!isConversationExist(convId)) {
            throw new NotExistingConversationException("No conversation with adressee. (Or just not started with partner)");
        } else {
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
        /*String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
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
        em.persist(newMessage);*/

    }

    //gets an arraylist of conversations belongs to user that logged in right now
    //if no conversations, throws exception
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


    //checks by id if conversation exists
    @Transactional
    public boolean isConversationExist(Long id) {
        Conversation c = conversationRepository.findConversationById(id);
        if (c == null) {
            return false;
        } else {
            return true;
        }
    }

    //checks by an username if exists
    @Transactional
    public boolean isPartnerExist(String partnerName) {
        User u = userRepository.findUserByName(partnerName);
        if (u == null) {
            return false;
        } else {
            return true;
        }
    }

    //gets all of the conversations from the db
    //throws error is no convers
    @Transactional
    public ArrayList<Conversation> getAllConversation() {
        List<Conversation> allConversation = em.createQuery("SELECT c FROM Conversation c", Conversation.class).getResultList();
        //nincs bemenő paramétere a lekérdezésnek
        if (allConversation.size() < 1) {
            throw new NotExistingConversationException("There is no conversations at all yet.");
        } else {
            return (ArrayList<Conversation>) allConversation;
        }
    }
 /* //nincs használatban
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
        return conversation;
    } */

     /* // nincs használatban
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
    } */
}
