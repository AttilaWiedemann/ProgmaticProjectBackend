package backend.controllers;

import backend.dto.messageDtos.ConversationDto;
import backend.exceptions.ExistingConversationException;
import backend.exceptions.NotExistingConversationException;
import backend.model.messageModels.Conversation;
import backend.services.messageServices.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class ConversationController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @RequestMapping(path = ("/conversation"), method = RequestMethod.GET)
    public ArrayList<Conversation> allConversation() {
        try {
            String logMessage = String.format("User requested all of related messages.");
            logger.info(logMessage);
            return conversationService.getAllConversationOfUser();
        } catch (NotExistingConversationException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping(path = ("/conversation"), method = RequestMethod.POST)
    public Conversation newConversation(@RequestBody ConversationDto conversationDto) {
        try {
            logger.info("Conversation started with " + conversationDto.getConvPartner());
            return conversationService.createConversation(conversationDto);
        } catch (ExistingConversationException ex) {
            logger.error("Conversation already started with this user");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);

        }
    }

    @RequestMapping(path = ("/conversation/{id}"), method = RequestMethod.GET)
    public Conversation oneConversation(@PathVariable("id") Long id) {
        return conversationService.getConversation(id);
    }
}
