package backend.controllers;

import backend.exceptions.NotValidFileException;
import backend.model.imageModels.DefaultImage;
import backend.model.imageModels.Image;
import backend.model.userModels.User;
import backend.repos.DefaultImageRepository;
import backend.repos.ImageRepository;
import backend.repos.UserRepository;
import backend.services.imageServices.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RestController
public class ImageController {
    private ImageService imageService;
    private ImageRepository imageRepository;
    private DefaultImageRepository defaultImageRepository;
    private UserRepository userRepository;

    @Autowired
    public ImageController(UserRepository userRepository ,DefaultImageRepository defaultImageRepository, ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.defaultImageRepository = defaultImageRepository;
        this.userRepository = userRepository;
    }


    @PostMapping("/rest/updatepicture")
    public ResponseEntity updateProfilePicture(MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        imageService.updateImageFile(file, user.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/rest/profilpicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public String imageUrl(@PathVariable("id") Long id) {
        if (id == 0) {
            return "/rest/profilpicture/0";
        } else {
            Image image = imageRepository.findByUserId(id);
            return image.getUrl();
        }

    }

    @GetMapping(path = "/rest/profilpicture/", produces = MediaType.IMAGE_JPEG_VALUE)
    public String profilePictureUrl() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.isHaveProfilePicture()) {
            return user.getProfilePicture().getUrl();
        }else {
            return "/rest/profilpicture/0";
        }
    }
    @GetMapping(path = "/rest/loadprofilpicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] sendImage (@PathVariable ("id") Long id){
        if (id==0){
            return defaultImageRepository.findByUrl("/rest/loadprofilpicture/0").getBytes();
        }
       return imageRepository.findImageById(id).getBytes();
    }
}
