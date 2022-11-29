package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.springframework.core.io.Resource;

@Data
@JsonAutoDetect
public class UploadedFileBean {
  private String name;
  private String originalName;
  private String type;
  private String url;
  private Long size;
  private transient Resource content;
}
