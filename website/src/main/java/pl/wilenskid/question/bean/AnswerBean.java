package pl.wilenskid.question.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.content.bean.ContentExtendedBean;

@Data
@JsonAutoDetect
public class AnswerBean {

  private Long id;
  private Long questionId;
  private ContentExtendedBean content;
  private Boolean isValid;

}
