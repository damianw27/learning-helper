package pl.wilenskid.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@Entity(name = "POSTS")
@EqualsAndHashCode(callSuper = true)
public class Post extends AbstractPersistable<Long> {

  private String title;

  @OneToOne
  private UploadedFile description;

  @OneToMany(cascade = CascadeType.ALL)
  private Set<User> contributors;

  @OneToMany(cascade = CascadeType.ALL)
  private Set<SubPost> subPosts;

  @ManyToMany()
  private Set<Tag> tags;

  private Calendar created;

  private Calendar updated;

}
