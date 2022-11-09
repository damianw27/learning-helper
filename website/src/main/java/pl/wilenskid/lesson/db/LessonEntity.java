package pl.wilenskid.lesson.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.section.db.SectionEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Calendar;
import java.util.Set;

@Setter
@Getter
@Entity(name = "LESSONS")
public class LessonEntity extends AbstractPersistable<Long> {

  private String title;

  private Calendar created;

  private Integer cardinalNumber;

  @ManyToOne
  private SectionEntity section;

  @OneToMany
  private Set<LessonContentEntity> lessonContents;
}
