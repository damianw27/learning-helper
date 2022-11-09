package pl.wilenskid.lesson.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.section.bean.SectionBean;

import java.util.List;

@Data
@JsonAutoDetect
public class LessonBean {

  private Long id;
  private String title;
  private Integer cardinalNumber;
  private String created;
  private SectionBean section;
  private List<LessonContentBean> lessonContents;

}
