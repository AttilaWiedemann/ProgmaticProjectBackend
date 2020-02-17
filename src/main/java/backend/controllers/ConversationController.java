package backend.controllers;

import backend.dto.messageDtos.ConversationDto;
import backend.model.messageModels.Conversation;
import backend.services.messageServices.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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

    @RequestMapping(path = ("/conversation"), method = RequestMethod.POST)
    public Conversation newConversation(@RequestBody ConversationDto conversationDto) {
        //Long convId = conversationService.createConversation(conversationDto, messageDto);
        return conversationService.createConversation(conversationDto);/*oneConversation(convId)*/
    }

    @RequestMapping(path = ("/conversation/{id}"), method = RequestMethod.GET) // id-s linkkel, alapján
    public Conversation oneConversation(@PathVariable("id") Long id) {
        return conversationService.getConversation(id);
    }
}
