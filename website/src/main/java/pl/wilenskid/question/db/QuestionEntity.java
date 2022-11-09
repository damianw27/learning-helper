package pl.wilenskid.question.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.course.db.CourseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity(name = "QUESTIONS")
public class QuestionEntity extends AbstractPersistable<Long> {

  private Long contentId;

  @OneToMany(fetch = FetchType.EAGER)
  private Set<AnswerEntity> answers;

  @ManyToOne
  private CourseEntity course;

}
