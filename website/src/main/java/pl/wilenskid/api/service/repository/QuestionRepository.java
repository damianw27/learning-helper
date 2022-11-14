package pl.wilenskid.api.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.api.model.Question;
import pl.wilenskid.api.model.SubPost;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
  List<Question> findAllBySubPost(SubPost subPost);
}
