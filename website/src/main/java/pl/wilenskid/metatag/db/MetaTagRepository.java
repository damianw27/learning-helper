package pl.wilenskid.metatag.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaTagRepository  extends CrudRepository<MetaTagEntity, Long> {
  Optional<MetaTagEntity> findByToken(String token);
}
