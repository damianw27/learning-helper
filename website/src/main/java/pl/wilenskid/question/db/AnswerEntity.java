package pl.wilenskid.question.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "ANSWERS")
public class AnswerEntity extends AbstractPersistable<Long> {

  private Long contentId;

  private Boolean isValid;

  @ManyToOne
  private QuestionEntity question;

}
