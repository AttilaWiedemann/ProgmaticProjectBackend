package backend.repos;

import backend.model.imageModels.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByUrl(String url);
    Image findImageById(Long id);
    Image findByUserId(Long id);
    void deleteById(Long id);
}
