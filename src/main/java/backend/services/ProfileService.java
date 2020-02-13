package backend.services;

import backend.dto.ProfileFilterDto;
import backend.dto.UserProfileWithVisibleFields;
import backend.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        UserProfileWithVisibleFields userProfileWithVisibleFields = createUserProfile(user);
        return userProfileWithVisibleFields;
    }

    //default = 20db
    @Transactional
    public List<UserProfileWithVisibleFields> listingExistingUsers(ProfileFilterDto profileFilter){
        /*
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //CriteriaBuilder létrehozása
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> userQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootUser = userQuery.from(User.class);
    //Criteriabuilderes szűrések:
        userQuery.select(rootUser).where(criteriaBuilder
                .greaterThan(rootUser.get(User_.BIRTH_DATE), LocalDateTime.now().minusYears(profileFilter.getMinAge())));
        */

        List<User> userList = em.createQuery("select u from User u").getResultList();
        List<User> userList1 = userList.stream()
                .filter(user -> getYearsBetweenDates(user.getBirthDate()) >= profileFilter.getMinAge() && getYearsBetweenDates(user.getBirthDate()) <= profileFilter.getMaxAge()).collect(Collectors.toList());
        return getResultList(userList1);
    }

    private int getYearsBetweenDates(LocalDate birthDate){
        long numberOfYears = ChronoUnit.YEARS.between(LocalDate.now(), birthDate);
        return (int) numberOfYears;
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
        userProfileWithVisibleFields.setId(user.getId());
        //userProfileWithVisibleFields.setImgUrl(user.getProfilePicture().getUrl());
        return userProfileWithVisibleFields;
    }



    //Returns ProfileFilterDto based on current users interests
    private ProfileFilterDto getUsersProfileFilterDto(){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProfileFilterDto profileFilterDto = new ProfileFilterDto();
        profileFilterDto.setStatingNumber(currentUser.getUserInterest().getMinAge());
        profileFilterDto.setEndingNumber(currentUser.getUserInterest().getMaxAge());
        profileFilterDto.setLookingFor(currentUser.getUserInterest().getInterest().toString());
        profileFilterDto.setStatingNumber(0);
        profileFilterDto.setEndingNumber(20);
        return profileFilterDto;
    }

}
