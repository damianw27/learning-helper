package pl.wilenskid.lesson.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect
public class LessonCreateBean {

  private Long sectionId;
  private String title;

}
