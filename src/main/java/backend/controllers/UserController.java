package backend.controllers;

import backend.dto.FilterDto;
import backend.dto.UserDto;
import backend.dto.UserInterestDto;
import backend.dto.UserProfileDto;
import backend.events.OnRegistrationCompleteEvent;
import backend.exceptions.ExistingUserException;
import backend.exceptions.NotExistingUserException;
import backend.model.User;
import backend.repos.UserRepository;
import backend.services.UserInterestService;
import org.graalvm.compiler.lir.LIRInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import backend.services.UserService;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 *
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private UserInterestService userInterestService;
    private ApplicationEventPublisher eventPublisher;

    //Constructor
    @Autowired
    public UserController(UserService userService,
                          UserInterestService userInterestService,
                          ApplicationEventPublisher eventPublisher) {

        this.userService = userService;
        this.userInterestService = userInterestService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Temporary or technical methods for DEBUG BUILD
     */
    @GetMapping("/get")
    public Long get(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id){
        User user = new User();
        try {
            user = userService.getUser(id);
        } catch (NotExistingUserException ex) {
            logger.error(ex.getMessage());
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
        return user;
    }

    @RequestMapping(path = ("/rest/user"), method = RequestMethod.GET)
    public UserDto sampleUser() {
        UserDto sampleUser = new UserDto();
        sampleUser.setEmail("e@mail.cim");
        sampleUser.setBirthDate(LocalDate.now().minusDays(2));
        sampleUser.setName("Ildi bácsi");
        return sampleUser;
    }

    @RequestMapping(path = ("/rest/test"), method = RequestMethod.GET)
    public FilterDto sampleFilter(){
        FilterDto sampleFilter = new FilterDto();
        sampleFilter.setMinAge(0);
        sampleFilter.setMaxAge(100);
        return sampleFilter;
    }


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
    
    @RequestMapping(path = ("/rest/user/profile/{id}"), method = RequestMethod.POST)
    public UserProfileDto userProfileDto(@RequestBody UserProfileDto userProfileDto) {
        return userService.addOptionalFields(userProfileDto);
    }
    @RequestMapping(path = ("/rest/user/profile/{id}"), method = RequestMethod.PUT)
    public UserProfileDto updateUserProfile(@RequestBody UserProfileDto userProfileDto, Long id) {
        return userService.addOptionalFields(userProfileDto);
    }
    @RequestMapping(path =("/rest/user/profile/{id}/interest"),method = RequestMethod.POST)
    public UserInterestDto addUserInteresttoProfile(@RequestBody UserInterestDto userInterestDto, Long id){
        return userInterestService.creatUserIterest(userInterestDto,id);
    }
}