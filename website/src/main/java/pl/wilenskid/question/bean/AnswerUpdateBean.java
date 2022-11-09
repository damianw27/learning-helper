package pl.wilenskid.question.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class AnswerUpdateBean {

  private Long id;
  private String content;
  private Boolean isValid;

}
