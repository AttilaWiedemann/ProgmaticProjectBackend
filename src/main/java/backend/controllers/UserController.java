package backend.controllers;

import backend.dto.userDtos.*;
import backend.events.OnRegistrationCompleteEvent;
import backend.exceptions.ExistingUserException;
import backend.model.userModels.User;
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


    @RequestMapping(path = ("/rest/profiles"), method = RequestMethod.POST)
    public List<UserProfileWithVisibleFields> getUserList(@RequestBody UserProfileFilterDto filterDto){
        return userService.listingExistingUsers(filterDto);
    }


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


    /**
     * Temporary or technical methods for DEBUG BUILD
     */
    @GetMapping("/get")
    public Long get(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}