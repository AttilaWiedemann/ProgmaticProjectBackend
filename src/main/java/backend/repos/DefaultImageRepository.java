package backend.repos;

import backend.model.imageModels.DefaultImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultImageRepository extends JpaRepository<DefaultImage,Long> {
    DefaultImage findByUrl(String url);
    DefaultImage findDefaultImageById(Long id);
}
