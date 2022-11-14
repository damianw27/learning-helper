package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.util.List;

@Data
@JsonAutoDetect
public class SubPostBean {
  private Long id;
  private String title;
  private Resource description;
  private Resource content;
  private List<UserBean> contributors;
  private String created;
  private String updated;
}
