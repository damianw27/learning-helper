package pl.wilenskid.api.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.api.model.UploadedFile;

import java.util.Optional;

@Repository
public interface UploadedFileRepository extends CrudRepository<UploadedFile, Long> {
  Optional<UploadedFile> findByName(String name);
}
