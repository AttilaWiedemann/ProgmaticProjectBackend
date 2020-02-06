package backend.controllers;

import backend.dto.UserDto;
import backend.dto.UserProfileDto;
import backend.enums.Gender;
import backend.enums.Intrest;
import backend.model.User;
import backend.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import backend.services.UserService;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @RequestMapping(path = ("/rest/user"), method = RequestMethod.GET)
    public UserDto sampleUser() {
        UserDto sampleUser = new UserDto();
        sampleUser.setEmail("e@mail.cim");
        sampleUser.setBirthDate(LocalDate.now().minusDays(2));
        //sampleUser.setGender(Gender.FÉRFI);
        //sampleUser.setIntrest(Intrest.NŐ);
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
        return userService.addOptionalFields(userProfileDto);
    }
    @RequestMapping(path = ("/rest/user/profile/{id}"), method = RequestMethod.PUT)
    public UserProfileDto updateUserProfile(@RequestBody UserProfileDto userProfileDto, Long id) {
        return userService.addOptionalFields(userProfileDto);
    }

}
