package backend.services.imageServices;

import backend.exceptions.ExistingUserException;
import backend.exceptions.NotValidFileException;
import backend.model.imageModels.Image;
import backend.model.userModels.User;
import backend.repos.ImageRepository;
import backend.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImageService {
    private ImageRepository imageRepository;
    private UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void setDefaultImageFile( Long id) {
        User user = userRepository.findUserById(id);
        user.setProfilePicture(imageRepository.findByUrl("rest/profilepicture/1"));
        em.persist(user);
    }

    @Transactional
    public void updateImageFile(MultipartFile file, Long id)  {
        byte[] byteObjects = convertToByte(file);
        User user = userRepository.findUserById(id);
        if (!user.getProfilePicture().getUrl().equals("rest/profilepicture/1")) {
        imageRepository.deleteById(user.getProfilePicture().getId());
        }
        user.setProfilePicture(null);
        saveImageFile(user,byteObjects);
    }

    public byte[] convertToByte(MultipartFile file) {
        byte[] byteObjects = new byte[0];
        try {
            byteObjects = new byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }
        } catch (IOException e) {
            throw new NotValidFileException("A fájlt nem lehet bytera bontani");
        }
        return byteObjects;
    }

    private void saveImageFile(User user, byte[] byteObjects){
        if (user.getProfilePicture() == null) {
            Image image = new Image();
            image.setUser(user);
            image.setBytes((byteObjects));
            em.persist(image);
            image.setUrl("/rest/picture/" + user.getName());
            image = imageRepository.findByUrl("/rest/picture/" + user.getName());
            user.setProfilePicture(image);
            image.setUrl("/rest/picture/" + image.getId());
            em.persist(image);
            em.persist(user);
        } else {
            throw new ExistingUserException("Már van feltöltve profilkép");
        }

    }
    public byte[] convertToByte(File file)  {
        byte[] byteObjects = new byte[0];
        try {
            byteObjects = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new NotValidFileException("Nem megfelelő képfájl");
        }
        return byteObjects;
    }
}

