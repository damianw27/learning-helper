package pl.wilenskid.participation.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wilenskid.course.db.CourseEntity;

import java.util.Optional;

@Repository
public interface ParticipationRepository extends CrudRepository<ParticipationEntity, Long> {
  Optional<ParticipationEntity> findByUserIdAndCourse(Long courseId, CourseEntity courseEntity);
}
