package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect
public class SubPostBean {
  private Long id;
  private Long postId;
  private String title;
  private FileBean description;
  private FileBean content;
  private String created;
  private String updated;
}
