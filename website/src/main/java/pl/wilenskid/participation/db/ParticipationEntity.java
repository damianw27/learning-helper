package pl.wilenskid.participation.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.course.db.CourseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Calendar;

@Getter
@Setter
@Entity(name = "PARTICIPATION")
public class ParticipationEntity extends AbstractPersistable<Long> {

  private Long userId;

  private Calendar created;

  @ManyToOne
  private CourseEntity course;

}
