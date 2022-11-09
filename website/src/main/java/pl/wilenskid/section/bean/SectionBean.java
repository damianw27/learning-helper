package pl.wilenskid.section.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import pl.wilenskid.content.bean.ContentExtendedBean;

@Data
@JsonAutoDetect
public class SectionBean {

  private Long id;
  private Long courseId;
  private String title;
  private ContentExtendedBean description;
  private Integer cardinalIndex;
  private String created;

}
