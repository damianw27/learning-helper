package pl.wilenskid.api.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.api.model.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
  Optional<Tag> findByToken(String token);
  Optional<Tag> findByTokenUpper(String tokenUpper);
}
