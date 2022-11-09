package pl.wilenskid.section.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class SectionCreateBean {

  private Long courseId;
  private String title;
  private Integer cardinalIndex;

}
