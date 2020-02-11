package backend.services;

import backend.dto.UserProfileWithVisibleFields;
import backend.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ProfileService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public UserProfileWithVisibleFields getProfileInfos() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileWithVisibleFields userProfileWithVisibleFields = new UserProfileWithVisibleFields(user.getBirthDate());
        userProfileWithVisibleFields.setAboutMe(user.getUserProfile().getAboutMe());
        userProfileWithVisibleFields.setBodyShape(user.getUserProfile().getBodyShape());
        userProfileWithVisibleFields.setCity(user.getUserProfile().getCity());
        userProfileWithVisibleFields.setEyeColor(user.getUserProfile().getEyeColor());
        userProfileWithVisibleFields.setHairColor(user.getUserProfile().getHairColor());
        userProfileWithVisibleFields.setHeight(user.getUserProfile().getHeight());
        userProfileWithVisibleFields.setLikesBooks(user.getUserInterest().isBooks());
        userProfileWithVisibleFields.setLikesCulture(user.getUserInterest().isCulture());
        userProfileWithVisibleFields.setLikesMovies(user.getUserInterest().isMovies());
        userProfileWithVisibleFields.setLikesMusic(user.getUserInterest().isMusic());
        userProfileWithVisibleFields.setLikesPolitics(user.getUserInterest().isPolitics());
        userProfileWithVisibleFields.setLikesSports(user.getUserInterest().isSports());
        userProfileWithVisibleFields.setLikesTravels(user.getUserInterest().isTravels());
        userProfileWithVisibleFields.setSmoking(user.getUserProfile().isSmoking());
        userProfileWithVisibleFields.setName(user.getName());
        //userProfileWithVisibleFields.setImgUrl(user.getProfilePicture().getUrl());

        return userProfileWithVisibleFields;

    }
}
