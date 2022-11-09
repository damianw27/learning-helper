package pl.wilenskid.content.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.user.bean.UserBean;

@Data
@JsonAutoDetect
public class ContentExtendedBean {

  private String name;
  private String type;
  private String url;
  private Long size;
  private UserBean owner;
  private String content;

}
