package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.api.model.QuestionAnswer;

import java.util.List;
import java.util.Optional;

@Data
@JsonAutoDetect
public class QuestionUpdateBean {
  private Long id;
  private String content;
  private List<QuestionAnswerUpdateBean> answers;

  public Optional<QuestionAnswerUpdateBean> getQuestionAnswerUpdateBean(QuestionAnswer questionAnswer) {
    return answers
      .stream()
      .filter(questionAnswerUpdateBean -> questionAnswerUpdateBean.getId().equals(questionAnswer.getId()))
      .findFirst();
  }
}
