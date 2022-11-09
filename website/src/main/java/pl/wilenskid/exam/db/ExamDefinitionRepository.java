package pl.wilenskid.exam.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamDefinitionRepository extends CrudRepository<ExamDefinitionEntity, Long> {
}
