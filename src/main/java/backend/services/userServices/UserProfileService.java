package backend.services.userServices;

import backend.dto.userDtos.UserProfileFilterDto;
import backend.dto.userDtos.UserProfileWithVisibleFields;
import backend.enums.Gender;
import backend.model.userModels.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @PersistenceContext
    EntityManager em;

    @Transactional
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
}