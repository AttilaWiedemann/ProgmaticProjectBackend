package backend.services;

import backend.model.Image;
import backend.repos.ImageRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    ImageRepositoryImpl imageRepository;

    @Autowired
    public ImageService(ImageRepositoryImpl imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public void saveImageFile( MultipartFile file) {
        try {

            byte[] byteObjects = new byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }
            Image image = new Image();
            image.setBytes((byteObjects));
            image.setUrl("/rest/picture/"+ image.getId());

            imageRepository.save(image);
        } catch (IOException e) {
            //todo handle better


            e.printStackTrace();
        }
    }
    }

