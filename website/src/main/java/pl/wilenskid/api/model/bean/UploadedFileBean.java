package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class UploadedFileBean {
  private String name;
  private String originalName;
  private String type;
  private String url;
  private Long size;
  private UserBean owner;
}
