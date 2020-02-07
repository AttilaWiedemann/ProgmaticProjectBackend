package backend.controllers;

import backend.model.Image;
import backend.repos.ImageRepository;
import backend.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
public class ImageController {
    private ImageService imageService;
    private ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageService imageService , ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @PostMapping("/rest/profilepicture")
    public void handleImagePost( @RequestParam("imagefile") MultipartFile file) {

        imageService.saveImageFile(file);

    }

    @GetMapping(path = "/rest/profilpicture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable("id") Long id){
        Image image = imageRepository.findById(id).get();
        return image.getBytes();
    }



}
