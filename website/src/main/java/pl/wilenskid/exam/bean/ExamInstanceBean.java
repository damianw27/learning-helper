package pl.wilenskid.exam.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.question.bean.QuestionBean;
import pl.wilenskid.user.bean.UserBean;

import java.util.List;

@Data
@JsonAutoDetect
public class ExamInstanceBean {

  private Long id;
  private UserBean user;
  private String startDateTime;
  private String endDateTime;
  private List<QuestionBean> questions;
  private String answers;
  private Double score;
  private Long courseId;
  private Long examDefinitionId;
  private String created;

}
