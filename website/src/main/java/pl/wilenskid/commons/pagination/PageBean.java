package pl.wilenskid.commons.pagination;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.List;

@Data
@JsonAutoDetect
public class PageBean<Bean> {

  private List<Bean> entries;
  private Integer maxEntriesCount;
  private PageInfoBean pageInfo;

}
