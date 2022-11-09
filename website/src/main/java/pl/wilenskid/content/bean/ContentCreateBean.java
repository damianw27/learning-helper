package pl.wilenskid.content.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class ContentCreateBean {
  private String fileName;
  private String fileUrl;
  private String fileType;
  private Long fileSize;
}
