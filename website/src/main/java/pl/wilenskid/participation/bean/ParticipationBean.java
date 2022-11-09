package pl.wilenskid.participation.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.course.bean.CourseBean;
import pl.wilenskid.user.bean.UserBean;

@Data
@JsonAutoDetect
public class ParticipationBean {

  private Long id;
  private UserBean user;
  private CourseBean course;
  private String created;

}
