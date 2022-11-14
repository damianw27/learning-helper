package pl.wilenskid.api.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.api.model.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
}
