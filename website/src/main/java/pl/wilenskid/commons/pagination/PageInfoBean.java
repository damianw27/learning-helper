package pl.wilenskid.commons.pagination;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class PageInfoBean {

  private Integer index;
  private Integer size;
  private Boolean isLast;

}
