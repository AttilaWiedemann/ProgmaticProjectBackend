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
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.userExists(user.getUsername())) {
            bindingResult.rejectValue("username", "username.exists", "Username is already taken!");
            return "register";
        }
        userService.createUser(user);
        return "redirect:/login";
    }


}
