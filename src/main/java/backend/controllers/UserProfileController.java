package backend.controllers;

import backend.model.User;
import backend.model.UserProfile;
import backend.repos.UserRepository;
import backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserProfileController {
    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = ("/rest/user/{id}"), method = RequestMethod.GET)
    public User sampleUser(Long id) {
        return userRepository.findUserById(id);
    }
    @RequestMapping(path = "",method = RequestMethod.PUT)
    public void userProfile (@RequestBody UserProfile userProfile , Long id){
        UserProfile modifiedUserProfile = new UserProfile();
        modifiedUserProfile.setAboutMe(userProfile.getAboutMe());
        modifiedUserProfile.setBodyShape(userProfile.getBodyShape());
        modifiedUserProfile.setCity(userProfile.getCity());
        modifiedUserProfile.setHeight(userProfile.getHeight());
        modifiedUserProfile.setSmoking(userProfile.isSmoking());

    }
}
