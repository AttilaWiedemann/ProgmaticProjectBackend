package backend.repos;

import backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByUrl(String url);
  Optional<Image> findById(Long id);
}
