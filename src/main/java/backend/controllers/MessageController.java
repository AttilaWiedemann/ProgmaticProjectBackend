package backend.controllers;

import backend.model.Conversation;
import backend.model.Message;
import backend.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {

    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(path = ("/messages/{id}"), method = RequestMethod.GET)
    public Conversation oneConversation(@PathVariable("id") Long convId) {
        return null;
    }
}
