package pl.wilenskid.lesson.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonContentRepository extends CrudRepository<LessonContentEntity, Long> {
}
