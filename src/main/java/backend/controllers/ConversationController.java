package backend.controllers;

import backend.dto.ConversationDto;
import backend.dto.MessageDto;
import backend.model.Conversation;
import backend.model.Message;
import backend.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConversationController {

    ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @RequestMapping(path = ("/conversation"), method = RequestMethod.POST) // /{id}? talán nem
    public Conversation newConversation(ConversationDto conversationDto, MessageDto messageDto) {
        Long convId = conversationService.createConversation(conversationDto, messageDto);
        return oneConversation(convId);
    }

    @RequestMapping(path = ("/conversation/{id}"), method = RequestMethod.GET)
    public Conversation oneConversation(@PathVariable("id") Long id) {
        return conversationService.getConversation(id);
    }
}
