package pl.wilenskid.user.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class UserAuthBean {

  private Boolean authenticated;
  private UserBean user;

}
