package pl.wilenskid.course.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.content.bean.ContentExtendedBean;
import pl.wilenskid.user.bean.UserBean;

import java.util.List;

@Data
@JsonAutoDetect
public class CourseBean {
  private Long id;
  private String title;
  private ContentExtendedBean description;
  private UserBean author;
  private String created;
  private List<Long> sectionsIds;
  private List<String> tags;
}
