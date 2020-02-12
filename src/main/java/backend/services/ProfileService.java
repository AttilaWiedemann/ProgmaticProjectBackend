package backend.services;

import backend.dto.ProfileFilterDto;
import backend.dto.UserProfileWithVisibleFields;
import backend.model.Message;
import backend.model.User;
import backend.model.UserInterest;
import backend.model.UserProfile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProfileService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public UserProfileWithVisibleFields getProfileInfos() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileWithVisibleFields userProfileWithVisibleFields = new UserProfileWithVisibleFields(user.getBirthDate());
        if(user.getUserProfile() != null) {
            userProfileWithVisibleFields.setAboutMe(user.getUserProfile().getAboutMe());
            userProfileWithVisibleFields.setBodyShape(user.getUserProfile().getBodyShape());
            userProfileWithVisibleFields.setCity(user.getUserProfile().getCity());
            userProfileWithVisibleFields.setEyeColor(user.getUserProfile().getEyeColor());
            userProfileWithVisibleFields.setHairColor(user.getUserProfile().getHairColor());
            userProfileWithVisibleFields.setHeight(user.getUserProfile().getHeight());
            userProfileWithVisibleFields.setSmoking(user.getUserProfile().isSmoking());
        }
        if(user.getUserInterest() != null) {
            userProfileWithVisibleFields.setLikesBooks(user.getUserInterest().isBooks());
            userProfileWithVisibleFields.setLikesCulture(user.getUserInterest().isCulture());
            userProfileWithVisibleFields.setLikesMovies(user.getUserInterest().isMovies());
            userProfileWithVisibleFields.setLikesMusic(user.getUserInterest().isMusic());
            userProfileWithVisibleFields.setLikesPolitics(user.getUserInterest().isPolitics());
            userProfileWithVisibleFields.setLikesSports(user.getUserInterest().isSports());
            userProfileWithVisibleFields.setLikesTravels(user.getUserInterest().isTravels());
        }
        userProfileWithVisibleFields.setName(user.getName());
        //userProfileWithVisibleFields.setImgUrl(user.getProfilePicture().getUrl());
        return userProfileWithVisibleFields;
    }

    //default = 20db
    @Transactional
    public List<UserProfileWithVisibleFields> listingExistingUsers(ProfileFilterDto profileFilter){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();
        int minAge = 0; int maxAge = 100; String lookingFor = "both"; int startingNumber = 0; int numberOfProfilesToShow = 20;
        if(currentUser.getUserInterest() != null){
            UserInterest usersInterest = currentUser.getUserInterest();
            if(usersInterest.getMinAge() != 0){
                minAge = usersInterest.getMinAge();
            } else { minAge = profileFilter.getMinAge();}
            if(usersInterest.getMaxAge() != 0){
                maxAge = usersInterest.getMaxAge();
            } else { maxAge = profileFilter.getMaxAge();}
            if(usersInterest.getInterest() != null){
                lookingFor = usersInterest.getInterest().toString();
            } else { lookingFor = profileFilter.getLookingFor();}  //string/enum
            startingNumber = profileFilter.getStatingNumber();
        }
        if(currentUser.getUserInterest() == null) {
            minAge = profileFilter.getMinAge();
            if(profileFilter.getMaxAge() != 0) {
                maxAge = profileFilter.getMaxAge();
            }
            startingNumber = profileFilter.getStatingNumber();
            if(profileFilter.getLookingFor() != null) {
                lookingFor = profileFilter.getLookingFor();
            }
        }
        if(profileFilter.getEndingNumber() == 0){
            numberOfProfilesToShow = 20;
        }else {numberOfProfilesToShow = profileFilter.getEndingNumber() - startingNumber;}

        int finalMaxAge = maxAge;
        int finalMinAge = minAge;

        List<User> userList = em.createQuery("select u from User u").getResultList();
        List<User> userList1 = userList.stream()
                .filter(user -> getYearsBetweenDates(user.getBirthDate()) <= finalMaxAge && getYearsBetweenDates(user.getBirthDate()) >= finalMinAge).collect(Collectors.toList());
        /*
        if(!lookingFor.equals("both")){
            String finalLookingFor = lookingFor;
            userStream.filter(u -> u.getUserProfile().getGender().equals(finalLookingFor));
        }*/

        //return getResultList(userList1);
        return getResultList(em.createQuery("select u from User u").getResultList());
    }

    private int getYearsBetweenDates(LocalDate birthDate){
        long numberOfYears = ChronoUnit.YEARS.between(LocalDate.now(), birthDate);
        return (int) numberOfYears;
    }

    private List<UserProfileWithVisibleFields> getResultList(List<User> userList){
        List<UserProfileWithVisibleFields> returnableList = new ArrayList<>();
        for(User user : userList){
            UserProfileWithVisibleFields userProfileWithVisibleFields = new UserProfileWithVisibleFields(user.getBirthDate());
            if(user.getUserProfile() != null) {
                userProfileWithVisibleFields.setAboutMe(user.getUserProfile().getAboutMe());
                userProfileWithVisibleFields.setBodyShape(user.getUserProfile().getBodyShape());
                userProfileWithVisibleFields.setCity(user.getUserProfile().getCity());
                userProfileWithVisibleFields.setEyeColor(user.getUserProfile().getEyeColor());
                userProfileWithVisibleFields.setHairColor(user.getUserProfile().getHairColor());
                userProfileWithVisibleFields.setHeight(user.getUserProfile().getHeight());
                userProfileWithVisibleFields.setSmoking(user.getUserProfile().isSmoking());
            }
            if(user.getUserInterest() != null) {
                userProfileWithVisibleFields.setLikesBooks(user.getUserInterest().isBooks());
                userProfileWithVisibleFields.setLikesCulture(user.getUserInterest().isCulture());
                userProfileWithVisibleFields.setLikesMovies(user.getUserInterest().isMovies());
                userProfileWithVisibleFields.setLikesMusic(user.getUserInterest().isMusic());
                userProfileWithVisibleFields.setLikesPolitics(user.getUserInterest().isPolitics());
                userProfileWithVisibleFields.setLikesSports(user.getUserInterest().isSports());
                userProfileWithVisibleFields.setLikesTravels(user.getUserInterest().isTravels());
            }
            userProfileWithVisibleFields.setName(user.getName());
            //userProfileWithVisibleFields.setImgUrl(user.getProfilePicture().getUrl());
            returnableList.add(userProfileWithVisibleFields);
        }
        return returnableList;
    }



}
