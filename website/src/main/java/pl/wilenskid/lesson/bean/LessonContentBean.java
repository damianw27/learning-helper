package pl.wilenskid.lesson.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.wilenskid.content.bean.ContentExtendedBean;
import pl.wilenskid.user.enums.LearningStyle;

@Data
@JsonAutoDetect
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class LessonContentBean {
  private Long id;
  private Long lessonId;
  private LearningStyle learningStyle;
  private ContentExtendedBean content;
}
