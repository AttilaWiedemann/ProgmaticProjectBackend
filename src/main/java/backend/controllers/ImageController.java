package backend.controllers;

import backend.model.imageModels.Image;
import backend.model.userModels.User;
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



@RestController
public class ImageController {
    private ImageService imageService;
    private ImageRepository imageRepository;
    private UserRepository userRepository;

    @Autowired
    public ImageController(UserRepository userRepository ,ImageService imageService , ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/rest/profilepicture")
    public ResponseEntity handleImagePost(MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        imageService.saveImageFile(file,user.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/rest/profilpicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable("id") Long id){
        Image image = imageRepository.findByUserId(id);
        return image.getBytes();
    }

    @GetMapping(path = "/rest/{userid}/profilpicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showProfilePicture(@PathVariable("id") int id, @PathVariable("userid") Long userid){
        return userRepository.findUserById(userid).getProfilePicture().getBytes();
       }

}
