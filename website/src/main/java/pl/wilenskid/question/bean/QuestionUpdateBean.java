package pl.wilenskid.question.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect
public class QuestionUpdateBean {

  private Long id;
  private String content;
  private List<AnswerUpdateBean> answers;

}
