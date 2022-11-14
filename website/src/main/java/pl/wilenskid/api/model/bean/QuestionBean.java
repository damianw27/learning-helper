package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.util.List;

@Data
@JsonAutoDetect
public class QuestionBean {
  private Long id;
  private Resource content;
  private List<QuestionAnswerBean> answers;
}
