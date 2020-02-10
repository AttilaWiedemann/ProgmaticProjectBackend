package backend.services;

import backend.model.VerificationToken;
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

    VerificationTokenRepository tokenRepository;

    @Autowired
    public TokenService(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public VerificationToken getTokenByToken(String token) {
        return tokenRepository.findVerificationTokenByToken(token);
    }




}