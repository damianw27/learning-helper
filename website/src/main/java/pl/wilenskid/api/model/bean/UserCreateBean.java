package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class UserCreateBean {
  private String name;
  private String displayName;
  private String email;
  private String password;
  private String rePassword;
}
