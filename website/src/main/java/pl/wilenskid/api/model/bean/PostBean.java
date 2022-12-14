package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.util.List;

@Data
@JsonAutoDetect
public class PostBean {
  private Long id;
  private String title;
  private FileBean description;
  private List<UserBean> contributors;
  private List<SubPostBean> subPosts;
  private List<TagBean> tags;
  private String created;
  private String updated;
}
