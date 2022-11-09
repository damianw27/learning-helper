package pl.wilenskid.course.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.metatag.db.MetaTagEntity;
import pl.wilenskid.participation.db.ParticipationEntity;
import pl.wilenskid.section.db.SectionEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@Entity(name = "COURSES")
public class CourseEntity extends AbstractPersistable<Long> {
  private String title;
  private Long descriptionContentId;
  private Long authorId;
  private Calendar created;
  @OneToMany(cascade = CascadeType.ALL)
  private Set<SectionEntity> sections;
  @OneToMany(cascade = CascadeType.ALL)
  private Set<ParticipationEntity> participants;
  @OneToMany(cascade = CascadeType.ALL)
  private Set<MetaTagEntity> tags;
}
