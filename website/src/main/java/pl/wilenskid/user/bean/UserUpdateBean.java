package pl.wilenskid.user.bean;

import lombok.Value;

@Value
public class UserUpdateBean {

  Long id;
  String name;
  String displayName;
  String email;
  String cityName;
  String country;
  String timezone;
  String updated;

}
