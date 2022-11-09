package pl.wilenskid.user.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.user.enums.LearningStyle;
import pl.wilenskid.user.enums.UserRole;

@Data
@JsonAutoDetect
public class UserBean {

  private Long id;
  private String name;
  private String displayName;
  private String email;
  private UserRole userRole;
  private String cityName;
  private String country;
  private String timezone;
  private String created;
  private String updated;
  private LearningStyle learningStyle;

}
