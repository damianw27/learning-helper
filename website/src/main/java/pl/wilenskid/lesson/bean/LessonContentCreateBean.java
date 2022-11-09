package pl.wilenskid.lesson.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import pl.wilenskid.user.enums.LearningStyle;

@Data
@JsonAutoDetect
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class LessonContentCreateBean {
  private LearningStyle learningStyle;
  private String content;
}
