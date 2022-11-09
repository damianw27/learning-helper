package pl.wilenskid.section.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.lesson.db.LessonEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@Entity(name = "SECTIONS")
public class SectionEntity extends AbstractPersistable<Long> {

  private String title;

  private Long descriptionContentId;

  private Integer cardinalIndex;

  private Calendar created;

  @ManyToOne
  private CourseEntity course;

  @OneToMany
  private Set<LessonEntity> lessons;

}
