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

import java.io.IOException;


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
    public ResponseEntity handleImagePost(MultipartFile file) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        imageService.setDefaultImageFile(file,user.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
//    @PostMapping("/rest/updatepicture")
//    public ResponseEntity updateProfilePicture(MultipartFile file){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//    }

    @GetMapping(path = "/rest/profilpicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public String imageUrl(@PathVariable("id") Long id){
        Image image = imageRepository.findByUserId(id);
        return image.getUrl();
    }

    @GetMapping(path = "/rest/profilpicture/", produces = MediaType.IMAGE_JPEG_VALUE)
    public String profilePictureUrl(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserById(user.getId()).getProfilePicture().getUrl();
       }
    @GetMapping(path = "/rest/profilepicture/{id}")
    public byte[] image(@PathVariable ("id")Long id){
        return imageRepository.findById(id).get().getBytes();
    }

}
