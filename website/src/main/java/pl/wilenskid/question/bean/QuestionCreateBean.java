package pl.wilenskid.question.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect
public class QuestionCreateBean {

  private Long courseId;
  private String content;
  private List<AnswerCreateBean> answers;

}
