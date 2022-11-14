package pl.wilenskid.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "QUESTIONS")
@EqualsAndHashCode(callSuper = true)
public class Question extends AbstractPersistable<Long> {

  @OneToOne
  private UploadedFile content;

  @OneToMany(fetch = FetchType.EAGER)
  private Set<QuestionAnswer> answers;

  @ManyToOne
  private SubPost subPost;

}
