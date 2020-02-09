package backend.controllers;

import backend.dto.ConversationDto;
import backend.dto.MessageDto;
import backend.model.Conversation;
import backend.model.Message;
import backend.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConversationController {

    ConversationService conversationService;

    //TODO hibaüzenetek
    //TODO végpontok kiírása, jó-e két bemenő

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @RequestMapping(path = ("/conversation"), method = RequestMethod.GET)
    public ArrayList<Conversation> allConversation() {
        return conversationService.getAllConversationOfUser();

    }

    @RequestMapping(path = ("/conversation"), method = RequestMethod.POST) // TODO kellenek a RequestBody-k?
    public Conversation newConversation(@RequestBody ConversationDto conversationDto,@RequestBody MessageDto messageDto) {
        Long convId = conversationService.createConversation(conversationDto, messageDto);
        return oneConversation(convId);
    }

    @RequestMapping(path = ("/conversation/{id}"), method = RequestMethod.GET)
    public Conversation oneConversation(@PathVariable("id") Long id) {
        return conversationService.getConversation(id);
    }
}
