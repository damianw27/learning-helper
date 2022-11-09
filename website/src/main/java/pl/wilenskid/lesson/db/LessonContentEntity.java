package pl.wilenskid.lesson.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.user.enums.LearningStyle;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@Entity(name = "LESSON_CONTENTS")
public class LessonContentEntity  extends AbstractPersistable<Long> {
  private Long contentId;

  private LearningStyle learningStyle;

  @ManyToOne
  private LessonEntity lesson;
}
