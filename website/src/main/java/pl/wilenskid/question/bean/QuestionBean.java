package pl.wilenskid.question.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.content.bean.ContentExtendedBean;

import java.util.List;

@Data
@JsonAutoDetect
public class QuestionBean {

  private Long id;
  private Long courseId;
  private ContentExtendedBean content;
  private List<AnswerBean> answers;

}
