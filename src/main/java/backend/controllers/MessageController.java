package backend.controllers;

import backend.dto.NewMessageDto;
import backend.model.Conversation;
import backend.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    ConversationService conversationService;

    @Autowired
    public MessageController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @RequestMapping(path = ("/message"), method = RequestMethod.POST) //TODO kellenek a @RequestBody-k?
    public Conversation newMessage(@RequestBody NewMessageDto newMessageDto) {
        Long id = newMessageDto.getId();
        String text = newMessageDto.getText();

        conversationService.createMessage(id, text);

        return conversationService.getConversation(newMessageDto.getId());
    }

}
