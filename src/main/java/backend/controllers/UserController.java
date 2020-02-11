package backend.controllers;

import backend.dto.UserDto;
import backend.dto.UserProfileDto;
import backend.model.User;
import backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import backend.services.UserService;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService,UserRepository userRepository ) {
        this.userService = userService;
        this.userRepository = userRepository;
    }



    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id){
       return userRepository.findUserById(id);
    }

    @RequestMapping(path = ("/rest/user"), method = RequestMethod.GET)
    public UserDto sampleUser() {
        UserDto sampleUser = new UserDto();
        sampleUser.setEmail("e@mail.cim");
        sampleUser.setBirthDate(LocalDate.now().minusDays(2));
        //sampleUser.setGender(Gender.MAN);
        //sampleUser.setIntrest(Intrest.WOMAN);
        sampleUser.setName("Ildi bácsi");
        sampleUser.setPassword("dolgok");
        return sampleUser;
    }


    @RequestMapping(path = ("/rest/register"), method = RequestMethod.POST)
    public UserDto register(@Valid @RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @RequestMapping(path = ("/rest/user/profile/{id}"), method = RequestMethod.POST)
    public UserProfileDto userProfileDto(@RequestBody UserProfileDto userProfileDto, Long id) {
        return userService.addOptionalFields(userProfileDto, id);
    }
    @RequestMapping(path = ("/rest/user/profile/{id}"), method = RequestMethod.PUT)
    public UserProfileDto updateUserProfile(@RequestBody UserProfileDto userProfileDto, Long id) {
        return userService.addOptionalFields(userProfileDto, id);
    }

}
