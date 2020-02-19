package backend.services.userServices;

import backend.dto.userDtos.UserDto;
import backend.dto.userDtos.UserProfileFilterDto;
import backend.dto.userDtos.UserProfileWithVisibleFields;
import backend.enums.*;
import backend.exceptions.ExistingUserException;
import backend.exceptions.NonExistingPageException;
import backend.exceptions.NotAuthenticatedUserException;
import backend.exceptions.NotExistingUserException;
import backend.model.imageModels.Image;
import backend.model.userModels.User;
import backend.model.userModels.UserInterest;
import backend.model.userModels.UserProfile;
import backend.model.emailModels.VerificationToken;
import backend.repos.ImageRepository;
import backend.repos.UserRepository;
import backend.services.emailServices.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private TokenService tokenService;
    private ImageRepository imageRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserService(ImageRepository imageRepository ,UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.imageRepository= imageRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        //TODO
        try {
            User user = userRepository.findUserByEmail(mail);

           // User user = em.createQuery("select u from User u left join fetch u.authorities where u.email=:email ", User.class)
             //       .setParameter("email", mail).getSingleResult();


            if(user.isEnabled() == true) {
                return user;
            }
            else {
                throw new UsernameNotFoundException("User was not found: " + mail);
            }
        } catch (NoResultException e) {
            //logger.debug("User was not found: {}", username);
            throw new UsernameNotFoundException("User was not found: " + mail);
        }
    }

    //CREATE
    @Transactional
    public User createUser(UserDto userDto){

        if (userRepository.findUserByEmail(userDto.getEmail()) == null) {
            User user = loadUserWithUserDto(userDto);
            user.setProfilePicture(imageRepository.findByUrl("/rest/profilepicture/1"));
            em.persist(user);
            return user;
        }
        else{
            throw new ExistingUserException(String.format("User already exist with %s email address."
                    , userDto.getEmail()));
        }
    }

    //READ

    @Transactional
    public UserProfileWithVisibleFields getUser(User user) {
        if (user != null){
            UserProfileWithVisibleFields userProfileWithVisibleFields = new UserProfileWithVisibleFields();
            addUserBaseDatas(userProfileWithVisibleFields, user);
            addUserProfileDatas(userProfileWithVisibleFields, user);
            addUserInterestDatas(userProfileWithVisibleFields, user);
            return userProfileWithVisibleFields;
        }
        else {
            throw new NotAuthenticatedUserException("Not Authenticated request");
        }

    }

    //default = 20db
    @Transactional
    public List<UserProfileWithVisibleFields> listingExistingUsers(UserProfileFilterDto profileFilter)
    throws NonExistingPageException
    {
        LocalDate less = LocalDate.now().minusYears(profileFilter.getMinAge());
        LocalDate more =  LocalDate.now().minusYears(profileFilter.getMaxAge());
        List<User> userList = userRepository.findAllByBirthDateIsLessThanEqualAndBirthDateGreaterThanEqual(less, more);
        switch (profileFilter.getLookingFor()){
            case MAN:
                return breakToPages(
                        profileFilter.getNumberPage(), profileFilter.getNumberSize(),
                        getResultList(userList.stream()
                        .filter(user -> user.getUserProfile() != null)
                        .filter(user -> user.getUserProfile().getGender().toString().equals("MAN"))
                        .collect(Collectors.toList())));
            case WOMAN:
                return breakToPages(
                        profileFilter.getNumberPage(), profileFilter.getNumberSize(),
                        getResultList(userList.stream()
                        .filter(user -> user.getUserProfile() != null)
                        .filter(user -> user.getUserProfile().getGender().toString().equals("WOMAN"))
                        .collect(Collectors.toList())));
            default:
                return breakToPages(
                        profileFilter.getNumberPage(), profileFilter.getNumberSize(), getResultList(userList));


        }
    }

    private List<UserProfileWithVisibleFields> breakToPages(int pageNumber, int pageSize,
                                            List<UserProfileWithVisibleFields> users) throws NonExistingPageException{
        int startingIndex = (pageNumber -1) * pageSize;
            if(startingIndex > users.size() - 1 || startingIndex < 0){
                throw new NonExistingPageException("Result list does not have this many pages: " + pageNumber);
            }
        int endingIndex = startingIndex + pageSize;
            if(endingIndex > users.size() - 1){
                endingIndex = users.size() -1;
            }
        return users.subList(startingIndex, endingIndex);
    }



    //UPDATE
    @Transactional
    public UserProfileWithVisibleFields updateUser(UserProfileWithVisibleFields updatedProfile) {

        User currentuser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentuser != null){
            long id = currentuser.getId();
            User user = em.find(User.class, id);

            if (user.getUserProfile() == null && user.getUserInterest() == null){
                UserProfile userProfile = new UserProfile();
                UserInterest userInterest = new UserInterest();
                //UserProfile beállítása
                generateUserProfileForUser(userProfile, updatedProfile);
                //UserInterest beállítása
                generateUserInterestForUser(userInterest, updatedProfile);
                //perzisztálása
                user.setUserProfile(userProfile);
                user.setUserInterest(userInterest);
                em.persist(userInterest);
                em.persist(userProfile);
                em.persist(user);
            }
            else{

                UserProfile userProfile = em.find(UserProfile.class, user.getUserProfile().getId());
                UserInterest userInterest = em.find(UserInterest.class, user.getUserInterest().getId());
                //UserProfile updatelés
                generateUserProfileForUser(userProfile, updatedProfile);
                //UserInterest beállítása
                generateUserInterestForUser(userInterest, updatedProfile);
                em.persist(userInterest);
                em.persist(userProfile);
            }
            return updatedProfile;
        }
        else {
            throw new NotAuthenticatedUserException("Not Authenticated request");
        }
    }

    @Transactional
    public VerificationToken getVerificationToken(String token) {
        return tokenService.getTokenByToken(token);
    }

    @Transactional
    public void saveRegisteredUser(User user) {
        user.setEnabled(true);
        em.persist(user);
    }


    //Returns list of userProfiles to display
    private List<UserProfileWithVisibleFields> getResultList(List<User> userList){
        List<UserProfileWithVisibleFields> returnableList = new ArrayList<>();
        for(User user : userList){
            UserProfileWithVisibleFields userProfileWithVisibleFields = getUser(user);
            returnableList.add(userProfileWithVisibleFields);
        }
        return returnableList;
    }

    private void generateUserProfileForUser (UserProfile userProfile, UserProfileWithVisibleFields updatedProfile){
        userProfile.setAboutMe(updatedProfile.getAboutMe());
        userProfile.setCity(updatedProfile.getCity());
        userProfile.setBodyShape(updatedProfile.getBodyShape());
        userProfile.setEyeColor(updatedProfile.getEyeColor());
        userProfile.setHairColor(updatedProfile.getHairColor());
        userProfile.setHoroscope(updatedProfile.getHoroscopeEnum());
        userProfile.setGender(updatedProfile.getGender());
        userProfile.setSmoking(updatedProfile.isSmoking());
    }

    private void generateUserInterestForUser(UserInterest userInterest, UserProfileWithVisibleFields updatedProfile){
        userInterest.setMovies(updatedProfile.isLikesMovies());
        userInterest.setSports(updatedProfile.isLikesSports());
        userInterest.setMusic(updatedProfile.isLikesMusic());
        userInterest.setBooks(updatedProfile.isLikesBooks());
        userInterest.setCulture(updatedProfile.isLikesCulture());
        userInterest.setTravels(updatedProfile.isLikesTravels());
        userInterest.setTechnology(updatedProfile.isLikesTechnology());
        userInterest.setPolitics(updatedProfile.isLikesPolitics());
        userInterest.setMinAge(updatedProfile.getMinAge());
        userInterest.setMaxAge(updatedProfile.getMaxAge());
        userInterest.setInterest(updatedProfile.getInterest());
    }

    private User loadUserWithUserDto(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setBirthDate(userDto.getBirthDate());
        return user;
    }

    private void addUserBaseDatas(UserProfileWithVisibleFields userProfileWithVisibleFields, User user) {
        userProfileWithVisibleFields.setId(user.getId());
        userProfileWithVisibleFields.setName(user.getName());
        userProfileWithVisibleFields.setBirthDate(user.getBirthDate());
    }

    private void addUserProfileDatas(UserProfileWithVisibleFields userProfileWithVisibleFields, User user) {
        if (user.getUserProfile() != null){
            UserProfile userProfile = user.getUserProfile();
            userProfileWithVisibleFields.setAboutMe(userProfile.getAboutMe());
            userProfileWithVisibleFields.setCity(userProfile.getCity());
            userProfileWithVisibleFields.setHeight(userProfile.getHeight());
            userProfileWithVisibleFields.setBodyShape(userProfile.getBodyShape());
            userProfileWithVisibleFields.setEyeColor(userProfile.getEyeColor());
            userProfileWithVisibleFields.setHairColor(userProfile.getHairColor());
            userProfileWithVisibleFields.setHoroscopeEnum(userProfile.getHoroscope());
            userProfileWithVisibleFields.setGender(userProfile.getGender());
            userProfileWithVisibleFields.setSmoking(userProfile.isSmoking());
        }
        else {
            userProfileWithVisibleFields.setAboutMe(null);
            userProfileWithVisibleFields.setCity(null);
            userProfileWithVisibleFields.setHeight(0);
            userProfileWithVisibleFields.setBodyShape(null);
            userProfileWithVisibleFields.setEyeColor(null);
            userProfileWithVisibleFields.setHairColor(null);
            userProfileWithVisibleFields.setHoroscopeEnum(null);
            userProfileWithVisibleFields.setGender(null);
            userProfileWithVisibleFields.setSmoking(false);
        }
    }

    private void addUserInterestDatas(UserProfileWithVisibleFields userProfileWithVisibleFields, User user) {
        if(user.getUserInterest() != null){
            UserInterest userInterest = user.getUserInterest();
            userProfileWithVisibleFields.setLikesMovies(userInterest.isMovies());
            userProfileWithVisibleFields.setLikesSports(userInterest.isSports());
            userProfileWithVisibleFields.setLikesMusic(userInterest.isMusic());
            userProfileWithVisibleFields.setLikesBooks(userInterest.isBooks());
            userProfileWithVisibleFields.setLikesCulture(userInterest.isCulture());
            userProfileWithVisibleFields.setLikesTravels(userInterest.isTravels());
            userProfileWithVisibleFields.setLikesTechnology(userInterest.isTechnology());
            userProfileWithVisibleFields.setLikesPolitics(userInterest.isPolitics());
            userProfileWithVisibleFields.setInterest(userInterest.getInterest());
            userProfileWithVisibleFields.setMinAge(userInterest.getMinAge());
            userProfileWithVisibleFields.setMaxAge(userInterest.getMaxAge());
        }
        else{
            userProfileWithVisibleFields.setLikesMovies(false);
            userProfileWithVisibleFields.setLikesSports(false);
            userProfileWithVisibleFields.setLikesMusic(false);
            userProfileWithVisibleFields.setLikesBooks(false);
            userProfileWithVisibleFields.setLikesCulture(false);
            userProfileWithVisibleFields.setLikesTravels(false);
            userProfileWithVisibleFields.setLikesTechnology(false);
            userProfileWithVisibleFields.setLikesPolitics(false);
            userProfileWithVisibleFields.setInterest(null);
            userProfileWithVisibleFields.setMinAge(0);
            userProfileWithVisibleFields.setMaxAge(0);
        }
    }
}