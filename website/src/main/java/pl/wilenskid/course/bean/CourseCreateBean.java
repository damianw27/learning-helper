package pl.wilenskid.course.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class CourseCreateBean {

  private String title;
  private String description;

}
