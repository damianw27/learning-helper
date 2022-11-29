package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class SubPostUpdateBean {
  private Long subPostId;
  private String title;
  private String description;
  private String content;
}
