package pl.wilenskid.lesson.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect
public class LessonUpdateBean {

  private Long id;
  private Long sectionId;
  private String title;
  private Integer cardinalNumber;
  private List<LessonContentUpdateBean> lessonContents;

}
