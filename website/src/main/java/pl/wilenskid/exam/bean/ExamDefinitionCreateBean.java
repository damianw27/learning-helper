package pl.wilenskid.exam.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class ExamDefinitionCreateBean {

  private Double passLevel;
  private Integer questionsCount;
  private Integer attemptsCount;
  private String startDateTime;
  private String endDateTime;
  private Long courseId;

}
