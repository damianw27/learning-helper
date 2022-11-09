package pl.wilenskid.user.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateBean {

  String name;
  String displayName;
  String email;
  String password;
  String rePassword;

}
