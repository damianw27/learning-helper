package pl.wilenskid.exam.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Calendar;

@Getter
@Setter
@Entity(name = "EXAM_INSTANCES")
public class ExamInstanceEntity extends AbstractPersistable<Long> {

  private Long userId;

  private Calendar startDateTime;

  private Calendar endDateTime;

  private String questions;

  private String answers;

  private Double score;

  private Long courseId;

  private Calendar created;

  @ManyToOne
  private ExamDefinitionEntity examDefinition;

}
