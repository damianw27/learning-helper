package pl.wilenskid.api.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.api.model.SubPost;

@Repository
public interface SubPostRepository extends CrudRepository<SubPost, Long> {
}
