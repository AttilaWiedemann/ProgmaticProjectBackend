package backend.controllers;

import backend.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileController {
    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /*@RequestMapping(path = ("/myProfile"), method = RequestMethod.GET)
    public UserDto2 myProfile() {
        return profileService.getProfileInfos();
    }*/
}
