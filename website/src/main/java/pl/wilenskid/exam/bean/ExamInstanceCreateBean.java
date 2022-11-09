package pl.wilenskid.exam.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class ExamInstanceCreateBean {

  private String startDateTime;
  private ExamDefinitionBean examDefinition;

}
