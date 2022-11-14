package pl.wilenskid.api.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.api.model.Question;
import pl.wilenskid.api.model.QuestionAnswer;

import java.util.List;

@Repository
public interface QuestionAnswerRepository extends CrudRepository<QuestionAnswer, Long> {
}
