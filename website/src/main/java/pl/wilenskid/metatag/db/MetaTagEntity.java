package pl.wilenskid.metatag.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.course.db.CourseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@Entity(name = "META_TAGS")
public class MetaTagEntity extends AbstractPersistable<Long> {
  private String token;
  @ManyToOne private CourseEntity course;
}
