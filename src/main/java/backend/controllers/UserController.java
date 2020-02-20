package backend.controllers;

import backend.dto.userDtos.*;
import backend.events.OnRegistrationCompleteEvent;
import backend.exceptions.ExistingUserException;
import backend.exceptions.NonExistingPageException;
import backend.exceptions.NotAuthenticatedUserException;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @PersistenceContext
    EntityManager em;

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
        try{
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = em.find(User.class, user.getId());
            UserProfileWithVisibleFields userProfileWithVisibleFields = userService.getUser(
                    currentUser
            );
            logger.info("Get User profile username: " + userProfileWithVisibleFields.getName());
            return userProfileWithVisibleFields;
        }catch (NotAuthenticatedUserException ex){
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping(path = "/rest/getUser/{id}", method = RequestMethod.GET)
    public UserProfileWithVisibleFields getById(@PathVariable("id") Long id){
        try {
            User user = em.find(User.class, id);
            return userService.getUser(user);
        }catch (NotAuthenticatedUserException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @RequestMapping(path = ("/rest/profiles"), method = RequestMethod.POST)
    public List<UserProfileWithVisibleFields> getUserList(@RequestBody UserProfileFilterDto filterDto){
        try {
            List<UserProfileWithVisibleFields> userList = userService.listingExistingUsers(filterDto);
            logger.info("Listing and filtering users");
            return userList;
        }catch (NonExistingPageException ex){
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    //UPDATE
    @RequestMapping(path = ("/rest/updateUser"), method = RequestMethod.POST)
    public UserProfileWithVisibleFields updateUser(@RequestBody UserProfileWithVisibleFields updatedProfile){

        try {
            UserProfileWithVisibleFields userProfileWithVisibleFields = userService.updateUser(updatedProfile);
            logger.info("User updated with: " + userProfileWithVisibleFields.getName());
            return userProfileWithVisibleFields;
        }catch (NotAuthenticatedUserException ex){
            logger.error(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
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