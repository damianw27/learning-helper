package pl.wilenskid.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@Entity(name = "SUB_POSTS")
@EqualsAndHashCode(callSuper = true)
public class SubPost extends AbstractPersistable<Long> {

  private String title;

  @OneToOne
  private UploadedFile description;

  @OneToOne
  private UploadedFile content;

  @OneToMany
  private Set<User> contributors;

  @ManyToOne
  private Post post;

  private Calendar created;

  private Calendar updated;

}

