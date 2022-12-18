package pl.wilenskid.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity(name = "QUESTION_ANSWERS")
public class QuestionAnswer extends AbstractPersistable<Long> {

  @OneToOne
  private UploadedFile content;

  private Boolean isValid;

  @ManyToOne
  private Question question;

}
