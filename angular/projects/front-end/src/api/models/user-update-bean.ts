package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.api.enums.UserRole;

@Data
@JsonAutoDetect
public class UserUpdateBean {
  private Long id;
  private String displayName;
  private String email;
  private UserRole userRole;
}
