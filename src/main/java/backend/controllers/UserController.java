package backend.controllers;

import backend.dto.UserDto;
import backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import backend.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
/*
    @RequestMapping(method = RequestMethod.GET)
    public String showRegister(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }
    */

    @RequestMapping(method = RequestMethod.POST)
    public Long register(@Valid @ModelAttribute("user") UserDto user) {

        return userService.createUser(user);
    }

}
