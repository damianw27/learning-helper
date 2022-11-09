package pl.wilenskid.section.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class SectionUpdateBean {

  private Long id;
  private Long courseId;
  private String title;
  private String description;
  private Integer cardinalIndex;

}
