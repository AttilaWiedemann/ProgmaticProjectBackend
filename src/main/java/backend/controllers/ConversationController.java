package backend.controllers;

import backend.dto.ConversationDto;
import backend.model.Conversation;
import backend.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversationController {

    ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @RequestMapping(path = ("/conversation"), method = RequestMethod.POST)
    public ConversationDto newConversation(ConversationDto conversationDto) {
        conversationService.createConversation(conversationDto);
    }

    @RequestMapping(path = ("/conversation/{id}"), method = RequestMethod.GET)
    public Conversation
}
