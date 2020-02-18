package backend.repos;

import backend.model.imageModels.Image;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class ImageRepositoryImpl  {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Image image){
        em.persist(image);
    }

}
