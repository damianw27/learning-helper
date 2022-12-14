package pl.wilenskid.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@Entity(name = "POSTS")
public class Post extends AbstractPersistable<Long> {

  private String title;

  @OneToOne
  private UploadedFile description;

  @ManyToMany
  @JoinTable(
    name = "CONTRIBUTORS",
    joinColumns = @JoinColumn(name = "POST_ID"),
    inverseJoinColumns = @JoinColumn(name = "USER_ID")
  )
  private Set<User> contributors;

  @OneToMany(mappedBy = "post")
  private Set<SubPost> subPosts;

  @ManyToMany()
  private Set<Tag> tags;

  private Calendar created;

  private Calendar updated;

}
