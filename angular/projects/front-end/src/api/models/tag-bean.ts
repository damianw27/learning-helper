package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class TagBean {
  private Long id;
  private String token;
  private String tokenUpper;
}
