package backend.controllers;

import backend.dto.MessageDto;
import backend.model.Conversation;
import backend.model.Message;
import backend.services.ConversationService;
import backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {

    ConversationService conversationService;

    @Autowired
    public MessageController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @RequestMapping(path = ("/message"), method = RequestMethod.POST)
    public Conversation newMessage(Long convId, MessageDto messageDto) {
        conversationService.createMessage(convId, messageDto);
        return conversationService.getConversation(convId);
    }

}
