package backend.utils;

import backend.enums.*;
import backend.model.messageModels.Conversation;
import backend.model.messageModels.ConversationMessage;
import backend.model.userModels.Authority;
import backend.model.userModels.User;
import backend.model.userModels.UserInterest;
import backend.model.userModels.UserProfile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Component
public class DBInitializer {

    @PersistenceContext
    private EntityManager em;

    @EventListener(ContextRefreshedEvent.class)
    public void onAppStartup(ContextRefreshedEvent ev) throws ServletException {
        DBInitializer me = ev.getApplicationContext().getBean(DBInitializer.class);
        me.init();
    }

    @Transactional
    public void init() {
        createAuthoritiesIfNotExist();
        createUsersIfNotExist();
        createOneConversationWithSomeMessages();
    }

    private void createOneConversationWithSomeMessages() {
        Conversation exampleConversation = new Conversation();
        exampleConversation.setConvStarter("Habi");
        exampleConversation.setConvPartner("UnfinishedUser");

        ConversationMessage firstExampleMessage = new ConversationMessage();
        firstExampleMessage.setAuthor("Habi");
        firstExampleMessage.setPartner("UnfinishedUser");
        firstExampleMessage.setCreationDate(LocalDateTime.now().minusHours(2));
        firstExampleMessage.setText("habitol unfinishednek elso uzenet");
        firstExampleMessage.setConversation(exampleConversation);

        ConversationMessage secondExampleMessage = new ConversationMessage();
        secondExampleMessage.setAuthor("UnfinishedUser");
        secondExampleMessage.setPartner("Habi");
        secondExampleMessage.setCreationDate(LocalDateTime.now().minusHours(1));
        secondExampleMessage.setText("unfinishedtol habinak (masodik uzenet)");
        secondExampleMessage.setConversation(exampleConversation);

        em.persist(exampleConversation);
        em.persist(firstExampleMessage);
        em.persist(secondExampleMessage);
    }

    private void createAuthoritiesIfNotExist() {
        long authorityCount = em.createQuery("select count(a) from Authority a", Long.class).getSingleResult();
        if (authorityCount == 0) {
            Authority userAuthority = new Authority("ROLE_USER");
            //Authority adminAuthority = new Authority("ROLE_ADMIN");
            em.persist(userAuthority);
            //em.persist(adminAuthority);
        }
    }

    private void createUsersIfNotExist() {
        long userCount = em.createQuery("select count(u) from User u", Long.class).getSingleResult();

        if (userCount == 0) {
            Authority userAuthority = em.createQuery("select a from Authority a where a.name = 'ROLE_USER'", Authority.class).getSingleResult();
            //Authority adminAuthority = em.createQuery("select a from Authority a where a.name = 'ROLE_ADMIN'", Authority.class).getSingleResult();
            /*
            User user = new User("First User", "alap", "habi@mail.hu", LocalDate.now());
            user.setEnabled(true);
            UserProfile userProfile = new UserProfile("Szeretem a sajtot", "Tahitótfalu", 2,
                    BodyShape.M, EyeColor.BROWN, HairColor.RED, Horoscope.GEMINI, false, Gender.MAN);
            UserInterest userInterest = new UserInterest(true, true, false, false,
                    true, true, false, false, Interest.WOMAN, 0, 25);
            user.setUserInterest(userInterest);
            user.setUserProfile(userProfile);
            user.setAuthorities(Collections.singleton(userAuthority));
            //User admin = new User("admin", "password", "admin@gmail.com", LocalDate.of(1997, 6, 11));
            //admin.setAuthorities(Collections.singleton(adminAuthority));
            em.persist(userInterest);
            em.persist(user);
            em.persist(userProfile);
            //em.persist(admin);
             */
            ArrayList<User> users = generateDummyUsers();
            for(User user : users){
                em.persist(user);
                if(user.getUserProfile() != null) {
                    em.persist(user.getUserInterest());
                }if(user.getUserInterest() != null) {
                    em.persist(user.getUserProfile());
                }
            }
        }
    }

    private ArrayList<User> generateDummyUsers(){
        ArrayList<User> users = new ArrayList<>();
        for(int i = 1; i < 21; i++){
    //Generating UserProfiles--------------
            Gender myGender = Gender.MAN;
            if(i % 2 == 0){
                myGender = Gender.WOMAN;
            }
            UserProfile userProfile = new UserProfile("A Progmatic Junior Programozó képzésén 5 hónap alatt tanulsz meg programozni egészen az alapoktól. Ezt követően elhelyezkedhetsz partnercégünk egyikénél, akik már a tanfolyam ideje alatt bemutatkoznak nektek, így az elhelyezkedés gyorsan és gördülékenyen zajlik majd",
                    "Budapest", (150 + (new Random().nextInt(50))), BodyShape.M, EyeColor.BROWN,
                    HairColor.BROWN, Horoscope.LEO, true, myGender);
    //Generating UserInterests--------------
            Interest myInterest = Interest.WOMAN;
            if(i % 2 == 0){
                myInterest = Interest.MAN;
            }
            Random r = new Random();
            UserInterest userInterest = new UserInterest(r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(),
                    r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), myInterest, 18, 80);
    //Generating Users----------------------
            LocalDate birthDate = LocalDate.now().minusYears(18 + (new Random().nextInt(50)));
            User user = new User("User" + i, "sajt", "User" + i + "@email.com", birthDate);
            user.setEnabled(true);
            user.setUserProfile(userProfile);
            user.setUserInterest(userInterest);
            users.add(user);
        }
    //Add unfinfished User------------------
            User user = new User("UnfinishedUser", "sajt", "Unfinished@email.com",
                    LocalDate.now().minusYears(20));
            user.setEnabled(true);
            users.add(user);
    //Adds Habi-----------------------------
            User habi = new User("Habi", "alap", "habi@mail.hu",
                    LocalDate.now().minusYears(33));
            users.add(habi);
            habi.setEnabled(true);
        return users;
    }

}
