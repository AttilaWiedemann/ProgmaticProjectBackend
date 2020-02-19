package backend.controllers;

import backend.dto.messageDtos.NewMessageDto;
import backend.exceptions.NotExistingConversationException;
import backend.model.messageModels.Conversation;
import backend.services.messageServices.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MessageController {

    ConversationService conversationService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public MessageController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    //creates new message in conversation with id (from newMessageDto)
    @RequestMapping(path = ("/message"), method = RequestMethod.POST)
    public Conversation newMessage(@RequestBody NewMessageDto newMessageDto) {
        logger.info("User trying to write new message");
        Long id = newMessageDto.getId();

        try {
            String text = newMessageDto.getText();
            conversationService.createMessage(id, text);
        } catch (NotExistingConversationException ex) {
            logger.info("User can't write message, no conversation (yet) with partner");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
        logger.info("User wrote a new message");
        return conversationService.getConversation(newMessageDto.getId());
    }

}
