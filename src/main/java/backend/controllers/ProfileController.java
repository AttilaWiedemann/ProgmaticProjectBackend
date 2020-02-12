package backend.controllers;

import backend.dto.ProfileFilterDto;
import backend.dto.UserProfileWithVisibleFields;
import backend.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfileController {
    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(path = ("/rest/myProfile"), method = RequestMethod.GET)
    public UserProfileWithVisibleFields myProfile() {
        return profileService.getProfileInfos();
    }

    @RequestMapping(path = ("/rest/profiles"), method = RequestMethod.GET)
    public List<UserProfileWithVisibleFields> getUserList(@RequestBody ProfileFilterDto filterDto){
        return profileService.listingExistingUsers(filterDto);
    }

    @RequestMapping(path = ("rest/myProfile"), method = RequestMethod.POST)
    public UserProfileWithVisibleFields updateMyProfile(@RequestBody UserProfileWithVisibleFields updatedProfile){
        //TODO profileservice updatelje az adatbázisban szereplő entitásokat
        return updatedProfile;
    }

}
