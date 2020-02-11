package backend.services;

import backend.enums.*;
import backend.model.Authority;
import backend.model.User;
import backend.model.UserInterest;
import backend.model.UserProfile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import java.time.LocalDate;
import java.util.Collections;

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




            User user = new User("First User", "alap", "habi@mail.hu", LocalDate.now());

            user.setEnabled(true);

            UserProfile userProfile = new UserProfile("Szeretem a sajtot", "Tahitótfalu", 2,
                    BodyShape.M, EyeColor.BROWN, HairColor.RED, Horoscope.GEMINI, false);
            UserInterest userInterest = new UserInterest(true, true, false, false, true, true, false, false, Interest.WOMAN, 20, 25);

            user.setUserInterest(userInterest);
            user.setUserProfile(userProfile);
            user.setAuthorities(Collections.singleton(userAuthority));
            //User admin = new User("admin", "password", "admin@gmail.com", LocalDate.of(1997, 6, 11));


            //admin.setAuthorities(Collections.singleton(adminAuthority));

            em.persist(userInterest);
            em.persist(user);
            em.persist(userProfile);
            //em.persist(admin);
        }
    }
}
