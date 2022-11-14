package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class QuestionAnswerCreateBean {
  private String content;
  private Boolean isValid;
}
