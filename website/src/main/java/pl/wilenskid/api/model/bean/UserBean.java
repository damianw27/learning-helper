package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.api.enums.UserRole;

@Data
@JsonAutoDetect
public class UserBean {
  private Long id;
  private String name;
  private String displayName;
  private String email;
  private UserRole userRole;
  private String created;
  private String updated;
}
