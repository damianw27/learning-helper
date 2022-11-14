package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.springframework.core.io.Resource;

@Data
@JsonAutoDetect
public class QuestionAnswerBean {
  private Long id;
  private Resource content;
  private Boolean isValid;
}
