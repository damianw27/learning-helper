package pl.wilenskid.exam.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.course.db.CourseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@Entity(name = "EXAM_DEFINITIONS")
public class ExamDefinitionEntity extends AbstractPersistable<Long> {

  private Double passLevel;

  private Integer questionsCount;

  private Integer attemptsCount;

  private Calendar startDateTime;

  private Calendar endDateTime;

  @ManyToOne
  private CourseEntity course;

  @OneToMany
  private Set<ExamInstanceEntity> instances;

}
