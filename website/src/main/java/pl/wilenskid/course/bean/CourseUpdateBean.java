package pl.wilenskid.course.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect
public class CourseUpdateBean {
  private Long id;
  private String title;
  private String description;
  private List<String> tags;
}
