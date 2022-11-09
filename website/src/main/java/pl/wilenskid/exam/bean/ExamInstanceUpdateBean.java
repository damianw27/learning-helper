package pl.wilenskid.exam.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class ExamInstanceUpdateBean {

  private Long id;
  private String endDateTime;
  private String answers;

}
