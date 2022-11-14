package pl.wilenskid.api.assembly;

import pl.wilenskid.api.model.SubPost;
import pl.wilenskid.api.model.bean.SubPostBean;
import pl.wilenskid.api.model.bean.UserBean;
import pl.wilenskid.api.service.FilesService;
import pl.wilenskid.api.service.TimeService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class SubPostAssembly {

  private final FilesService filesService;
  private final UserAssembly userAssembly;
  private final TimeService timeService;

  @Inject
  public SubPostAssembly(FilesService filesService,
                         UserAssembly userAssembly,
                         TimeService timeService) {
    this.filesService = filesService;
    this.timeService = timeService;
    this.userAssembly = userAssembly;
  }

  public SubPostBean toBean(SubPost subPost) {
    List<UserBean> contributors = subPost
      .getContributors()
      .stream()
      .map(userAssembly::toBean)
      .collect(Collectors.toList());

    String created = timeService
      .dateToString(subPost.getCreated(), false)
      .orElse(null);

    String updated = timeService
      .dateToString(subPost.getUpdated(), false)
      .orElse(null);

    SubPostBean subPostBean = new SubPostBean();
    subPostBean.setId(subPost.getId());
    subPostBean.setTitle(subPost.getTitle());
    subPostBean.setDescription(filesService.download(subPost.getDescription().getName()));
    subPostBean.setContent(filesService.download(subPost.getContent().getName()));
    subPostBean.setContributors(contributors);
    subPostBean.setCreated(created);
    subPostBean.setUpdated(updated);
    return subPostBean;
  }

}
