package backend.services;

import backend.model.Image;
import backend.model.User;
import backend.repos.ImageRepository;
import backend.repos.ImageRepositoryImpl;
import backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

@Service
public class ImageService {
    private ImageRepository imageRepository;
    private UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository=userRepository;
    }

    @Transactional
    public void saveImageFile(MultipartFile file, Long id) {
        try {

            byte[] byteObjects = new byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }
            User user = userRepository.findUserById(id);
            Image image = new Image();
            image.setBytes((byteObjects));
            em.persist(image);
            image.setUrl("/rest/picture/"+ user.getName());
            image = imageRepository.findByUrl("/rest/picture/"+ user.getName());
            user.setProfilePicture(image);
            image.setUrl("/rest/picture/"+ user.getName() + image.getId());
            em.persist(image);
            em.persist(user);



        } catch (IOException e) {



            e.printStackTrace();
        }
    }
    }

