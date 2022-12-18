package pl.wilenskid.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Calendar;

@Getter
@Setter
@Entity(name = "SUB_POSTS")
public class SubPost extends AbstractPersistable<Long> {

  private String title;

  @OneToOne
  private UploadedFile description;

  @OneToOne
  private UploadedFile content;

  @ManyToOne
  @JoinColumn(name = "POST_ID", nullable = false)
  private Post post;

  private Calendar created;

  private Calendar updated;

}

