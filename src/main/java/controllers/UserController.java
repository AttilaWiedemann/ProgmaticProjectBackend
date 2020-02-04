package controllers;

import model.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/messages")
public class UserController {
    @RequestMapping(method = RequestMethod.GET)
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public Long register(@Valid @ModelAttribute("user") User user) {


        return userService.createUser(user);
    }


}
