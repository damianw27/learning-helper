package pl.wilenskid.exam.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.course.bean.CourseBean;

import java.util.Set;

@Data
@JsonAutoDetect
public class ExamDefinitionBean {

  private Long id;
  private Double passLevel;
  private Integer questionsCount;
  private Integer attemptsCount;
  private String startDateTime;
  private String endDateTime;
  private CourseBean course;
  private Set<ExamInstanceBean> instances;

}
