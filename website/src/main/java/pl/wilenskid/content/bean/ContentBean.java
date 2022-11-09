package pl.wilenskid.content.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.user.bean.UserBean;

@Data
@JsonAutoDetect
public class ContentBean {

  private String name;
  private String url;
  private UserBean owner;

}
