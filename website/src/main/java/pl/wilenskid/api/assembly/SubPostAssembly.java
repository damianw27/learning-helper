package pl.wilenskid.api.assembly;

import pl.wilenskid.api.model.SubPost;
import pl.wilenskid.api.model.bean.SubPostBean;
import pl.wilenskid.api.service.TimeService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SubPostAssembly {

  private final FileAssembly fileAssembly;
  private final TimeService timeService;

  @Inject
  public SubPostAssembly(FileAssembly fileAssembly,
                         TimeService timeService) {
    this.fileAssembly = fileAssembly;
    this.timeService = timeService;
  }

  public SubPostBean toBean(SubPost subPost) {
    String created = timeService
      .dateToString(subPost.getCreated(), false)
      .orElse(null);

    String updated = timeService
      .dateToString(subPost.getUpdated(), false)
      .orElse(null);

    SubPostBean subPostBean = new SubPostBean();
    subPostBean.setId(subPost.getId());
    subPostBean.setPostId(subPost.getPost().getId());
    subPostBean.setTitle(subPost.getTitle());
    subPostBean.setDescription(fileAssembly.toBean(subPost.getDescription()));
    subPostBean.setContent(fileAssembly.toBean(subPost.getContent()));
    subPostBean.setCreated(created);
    subPostBean.setUpdated(updated);
    return subPostBean;
  }

}
