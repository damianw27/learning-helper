package pl.wilenskid.exam.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.course.bean.CourseBean;
import pl.wilenskid.question.bean.QuestionBean;

import java.util.List;

@Data
@JsonAutoDetect
public class ExamMetaDataBean {

  private List<QuestionBean> questions;
  private CourseBean course;
  private ExamDefinitionBean examDefinition;

}
