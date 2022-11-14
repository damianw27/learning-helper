package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect
public class QuestionCreateBean {
  private Long subPageId;
  private String content;
  private List<QuestionAnswerCreateBean> answers;
}
