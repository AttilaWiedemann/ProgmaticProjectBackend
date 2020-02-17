package backend.services.emailServices;

import backend.model.userModels.User;
import backend.model.emailModels.VerificationToken;
import backend.repos.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class TokenService {

    @PersistenceContext
    EntityManager em;

    private VerificationTokenRepository tokenRepository;

    @Autowired
    public TokenService(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public void createVerificationToken(User user, String token) {
        VerificationToken vToken = new VerificationToken();
        vToken.setToken(token);
        vToken.setUser(user);
        em.persist(vToken);
        user.setToken(vToken);
        em.persist(user);
    }

    @Transactional
    public VerificationToken getTokenByToken(String token) {
        return tokenRepository.findVerificationTokenByToken(token);
    }




}