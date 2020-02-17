package backend.services.userServices;

import backend.dto.userDtos.UserDto;
import backend.dto.userDtos.UserProfileWithVisibleFields;
import backend.enums.*;
import backend.exceptions.ExistingUserException;
import backend.exceptions.NotExistingUserException;
import backend.model.userModels.User;
import backend.model.userModels.UserInterest;
import backend.model.userModels.UserProfile;
import backend.model.emailModels.VerificationToken;
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

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private TokenService tokenService;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        //TODO
        try {
            User user = userRepository.findUserByEmail(mail);
            /*
            User user = em.createQuery("select u from User u left join fetch u.authorities where u.email=:email ", User.class)
                    .setParameter("email", mail).getSingleResult();

            */
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
    public UserProfileWithVisibleFields getUser() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileWithVisibleFields userProfileWithVisibleFields = new UserProfileWithVisibleFields();
        addUserBaseDatas(userProfileWithVisibleFields, user);
        addUserProfileDatas(userProfileWithVisibleFields, user);
        addUserInterestDatas(userProfileWithVisibleFields, user);
        return userProfileWithVisibleFields;
    }





    //UPDATE
    @Transactional
    public UserProfileWithVisibleFields updateUser(UserProfileWithVisibleFields updatedProfile) {

        User currentuser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = currentuser.getId();
        User user = em.find(User.class, id);

        if (user.getUserProfile() == null || user.getUserInterest() == null){
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

    //DELETE
    @Transactional
    public void deleteUser(){
        //TODO
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

    private void generateUserProfileForUser (UserProfile userProfile, UserProfileWithVisibleFields updatedProfile){
        userProfile.setAboutMe(updatedProfile.getAboutMe());
        userProfile.setCity(updatedProfile.getCity());
        userProfile.setBodyShape(BodyShape.valueOf(updatedProfile.getBodyShape()));
        userProfile.setEyeColor(EyeColor.valueOf(updatedProfile.getEyeColor()));
        userProfile.setHairColor(HairColor.valueOf(updatedProfile.getHairColor()));
        userProfile.setHoroscope(Horoscope.valueOf(updatedProfile.getHoroscopeEnum()));
        userProfile.setGender(Gender.valueOf(updatedProfile.getGender()));
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
        userInterest.setInterest(Interest.valueOf(updatedProfile.getInterest()));
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
            userProfileWithVisibleFields.setBodyShape(userProfile.getBodyShape().toString());
            userProfileWithVisibleFields.setEyeColor(userProfile.getEyeColor().toString());
            userProfileWithVisibleFields.setHairColor(userProfile.getHairColor().toString());
            userProfileWithVisibleFields.setHoroscopeEnum(userProfile.getHoroscope().toString());
            userProfileWithVisibleFields.setGender(userProfile.getGender().toString());
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
            userProfileWithVisibleFields.setInterest(userInterest.getInterest().toString());
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



    /*
    *
    *     @Transactional
    public UserProfileWithVisibleFields getProfileInfos() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileWithVisibleFields userProfileWithVisibleFields = createUserProfile(user);
        return userProfileWithVisibleFields;
    }

    //default = 20db
    @Transactional
    public List<UserProfileWithVisibleFields> listingExistingUsers(UserProfileFilterDto profileFilter) {
        List<User> userList = em.createQuery("select u from User u where u.birthDate < :first and u.birthDate > :second")
                .setParameter("first", LocalDate.now().minusYears(profileFilter.getMinAge()))
                .setParameter("second", LocalDate.now().minusYears(profileFilter.getMaxAge())).getResultList();
        switch (profileFilter.getLookingFor()){
            case "Man":
                return getResultList(userList.stream()
                        .filter(user -> user.getUserProfile().getGender().toString().equals("MAN"))
                        .collect(Collectors.toList()));
            case "Woman":
                return getResultList(userList.stream()
                        .filter(user -> user.getUserProfile().getGender().toString().equals("WOMAN"))
                        .collect(Collectors.toList()));
            default:
                return getResultList(userList);
        }
    }

    //Returns list of userProfiles to display
    private List<UserProfileWithVisibleFields> getResultList(List<User> userList){
        List<UserProfileWithVisibleFields> returnableList = new ArrayList<>();
        for(User user : userList){
            UserProfileWithVisibleFields userProfileWithVisibleFields = createUserProfile(user);
            returnableList.add(userProfileWithVisibleFields);
        }
        return returnableList;
    }

    //Returns UserProfileWithVisibleFields of a user with every displayable data
    private UserProfileWithVisibleFields createUserProfile(User user){
        UserProfileWithVisibleFields userProfileWithVisibleFields = new UserProfileWithVisibleFields(user.getBirthDate());
        if(user.getUserProfile() != null) {
            userProfileWithVisibleFields.setAboutMe(user.getUserProfile().getAboutMe());
            userProfileWithVisibleFields.setBodyShape(user.getUserProfile().getBodyShape().toString());
            userProfileWithVisibleFields.setCity(user.getUserProfile().getCity());
            userProfileWithVisibleFields.setEyeColor(user.getUserProfile().getEyeColor().toString());
            userProfileWithVisibleFields.setHairColor(user.getUserProfile().getHairColor().toString());
            userProfileWithVisibleFields.setHeight(user.getUserProfile().getHeight());
            userProfileWithVisibleFields.setSmoking(user.getUserProfile().isSmoking());
            userProfileWithVisibleFields.setGender(user.getUserProfile().getGender().toString());
        }
        if(user.getUserInterest() != null) {
            userProfileWithVisibleFields.setLikesBooks(user.getUserInterest().isBooks());
            userProfileWithVisibleFields.setLikesCulture(user.getUserInterest().isCulture());
            userProfileWithVisibleFields.setLikesMovies(user.getUserInterest().isMovies());
            userProfileWithVisibleFields.setLikesMusic(user.getUserInterest().isMusic());
            userProfileWithVisibleFields.setLikesPolitics(user.getUserInterest().isPolitics());
            userProfileWithVisibleFields.setLikesSports(user.getUserInterest().isSports());
            userProfileWithVisibleFields.setLikesTravels(user.getUserInterest().isTravels());
            userProfileWithVisibleFields.setInterest(user.getUserInterest().getInterest().toString());
            userProfileWithVisibleFields.setMinAge(user.getUserInterest().getMinAge());
            userProfileWithVisibleFields.setMaxAge(user.getUserInterest().getMaxAge());
        }
        userProfileWithVisibleFields.setName(user.getName());
        userProfileWithVisibleFields.setId(user.getId());
        userProfileWithVisibleFields.setBirthDate(user.getBirthDate());
        //userProfileWithVisibleFields.setImgUrl(user.getProfilePicture().getUrl());
        return userProfileWithVisibleFields;
    }

    //Generates Enum based on filter
    private Enum<Gender> generateEnum(String userInterest){
        switch (userInterest){
            case "Man":
                return Gender.MAN;
            case "Woman":
                return Gender.WOMAN;
            default:    //Method is called within an if statement, never reaches default return
                return null;
        }
    }
    *
    *
    * */
}