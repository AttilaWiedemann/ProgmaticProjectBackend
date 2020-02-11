package backend.controllers;

import backend.dto.UserDto;
import backend.dto.UserProfileDto;
<<<<<<< HEAD
import backend.enums.Gender;
import backend.enums.Intrest;
import backend.events.OnRegistrationCompleteEvent;
=======
>>>>>>> 6bf45725bfdba22ceae466cca84ec1ef71e93992
import backend.model.User;
import backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.Model;
=======
>>>>>>> 6bf45725bfdba22ceae466cca84ec1ef71e93992
import org.springframework.web.bind.annotation.*;
import backend.services.UserService;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
<<<<<<< HEAD
    ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserController(UserService userService ,UserInterestService userInterestService) {
=======
    public UserController(UserService userService,UserRepository userRepository ) {
>>>>>>> 6bf45725bfdba22ceae466cca84ec1ef71e93992
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
    public UserDto register(@Valid @RequestBody UserDto user, WebRequest request) {
        UserDto userDtoResponse = userService.createUser(user);
        User unVerificatedUser = null;

        if (userDtoResponse != null){
            unVerificatedUser = userService.getUserByEmail(userDtoResponse.getEmail());
        }

        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(unVerificatedUser, request.getLocale(), appUrl));
        } catch (Exception me) {

        }

        return userDtoResponse;
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
