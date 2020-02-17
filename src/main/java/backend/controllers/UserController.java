package backend.controllers;

import backend.dto.userDtos.*;
import backend.events.OnRegistrationCompleteEvent;
import backend.exceptions.ExistingUserException;
import backend.model.userModels.User;
import backend.services.userServices.UserInterestService;
import backend.services.userServices.UserProfileService;
import backend.services.userServices.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    //CREATE
    @RequestMapping(path = ("/rest/register"), method = RequestMethod.POST)
    public UserDto register(@Valid @RequestBody UserDto userDto, WebRequest request) {

        try {
            User user = userService.createUser(userDto);
            String logMessage = String.format("User created with %s email address.", userDto.getEmail());
            logger.info(logMessage);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
            logger.info("Registration complete event triggered.");
            return userDto;
        } catch (ExistingUserException ex) {
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (Exception ex){
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    //READ
    @RequestMapping(path = ("/rest/getUser"), method = RequestMethod.GET)
    public UserProfileWithVisibleFields getUser(){
        return userService.getUser();
    }

    /*
    @RequestMapping(path = ("/rest/profiles"), method = RequestMethod.POST)
    public List<UserProfileWithVisibleFields> getUserList(@RequestBody UserProfileFilterDto filterDto){
        return userProfileService.listingExistingUsers(filterDto);
    }
    */

    //UPDATE
    @RequestMapping(path = ("/rest/updateUser"), method = RequestMethod.POST)
    public UserProfileWithVisibleFields updateUser(@RequestBody UserProfileWithVisibleFields updatedProfile){
        return userService.updateUser(updatedProfile);
    }

    //DELETE
    @RequestMapping(path = ("/rest/deleteUser"), method = RequestMethod.DELETE)
    public void deleteUser(){
        //TODO
    }



/*

    @RequestMapping(path = ("/rest/myProfile"), method = RequestMethod.GET)
    public UserProfileWithVisibleFields myProfile() {
        return userProfileService.getProfileInfos();
    }



    @RequestMapping(path = ("rest/myProfile"), method = RequestMethod.POST)
    public UserProfileWithVisibleFields updateMyProfile(@RequestBody UserProfileWithVisibleFields updatedProfile){
        //TODO profileservice updatelje az adatbázisban szereplő entitásokat
        return updatedProfile;
    }


    @RequestMapping(path = ("/rest/user/updateUser"), method = RequestMethod.POST)
    public UserProfileWithVisibleFields userProfileDto(@RequestBody UserProfileWithVisibleFields updatedProfile) {
        return userService.addOptionalFields(updatedProfile);
    }


    */


/*
    @RequestMapping(path = ("/rest/getProfileDto"), method = RequestMethod.GET)
    public UserProfileDto getUserDto(){
        UserProfileDto userdto = new UserProfileDto();
        userdto.setAboutMe("Ez az updatelt about me");
        return userdto;
    }

    @RequestMapping(path = ("/rest/getUserProfileDtoWithVisibleFields"))
    public UserProfileWithVisibleFields getUserProfileForUpdateTesting(){
        UserProfileWithVisibleFields profileToReturn = new UserProfileWithVisibleFields();
        profileToReturn.setCity("Nyíregyháza");
        return profileToReturn;
    }

    @RequestMapping(path = ("/rest/updateUser"), method = RequestMethod.PUT)
    public UserProfileDto updateUserProfile(@RequestBody UserProfileDto userProfileDto, Long id) {
        return userService.addOptionalFields(userProfileDto);
    }

    @RequestMapping(path =("/rest/user/profile/{id}/interest"),method = RequestMethod.POST)
    public UserInterestDto addUserInteresttoProfile(@RequestBody UserInterestDto userInterestDto, Long id){
        return userInterestService.creatUserIterest(userInterestDto,id);
    }
    */











    /**
     * Temporary or technical methods for DEBUG BUILD
     */
    @GetMapping("/get")
    public Long get(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
    @RequestMapping(path = ("/rest/test"), method = RequestMethod.GET)
    public UserFilterDto sampleFilter(){
        UserFilterDto sampleFilter = new UserFilterDto();
        sampleFilter.setMinAge(0);
        sampleFilter.setMaxAge(100);
        return sampleFilter;
    }
}