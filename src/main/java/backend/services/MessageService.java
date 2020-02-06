package backend.services;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class MessageService {
    @PersistenceContext
    EntityManager em;
}
