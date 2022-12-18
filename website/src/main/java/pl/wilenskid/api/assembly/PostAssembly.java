package pl.wilenskid.api.assembly;

import pl.wilenskid.api.model.Post;
import pl.wilenskid.api.model.bean.PostBean;
import pl.wilenskid.api.model.bean.SubPostBean;
import pl.wilenskid.api.model.bean.TagBean;
import pl.wilenskid.api.model.bean.UserBean;
import pl.wilenskid.api.service.TimeService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class PostAssembly {

  private final SubPostAssembly subPostAssembly;
  private final UserAssembly userAssembly;
  private final FileAssembly fileAssembly;
  private final TagAssembly tagAssembly;
  private final TimeService timeService;

  @Inject
  public PostAssembly(SubPostAssembly subPostAssembly,
                      UserAssembly userAssembly,
                      FileAssembly fileAssembly,
                      TagAssembly tagAssembly,
                      TimeService timeService) {
    this.subPostAssembly = subPostAssembly;
    this.userAssembly = userAssembly;
    this.fileAssembly = fileAssembly;
    this.tagAssembly = tagAssembly;
    this.timeService = timeService;
  }

  public PostBean toBean(Post post) {
    List<SubPostBean> subPosts = post
      .getSubPosts()
      .stream()
      .map(subPostAssembly::toBean)
      .collect(Collectors.toList());

    List<UserBean> contributors = post
      .getContributors()
      .stream()
      .map(userAssembly::toBean)
      .collect(Collectors.toList());

    List<TagBean> tags = post
      .getTags()
      .stream()
      .map(tagAssembly::toBean)
      .collect(Collectors.toList());

    String created = timeService
      .dateToString(post.getCreated(), false)
      .orElse(null);

    String updated = timeService
      .dateToString(post.getUpdated(), false)
      .orElse(null);

    PostBean postBean = new PostBean();
    postBean.setId(post.getId());
    postBean.setTitle(post.getTitle());
    postBean.setDescription(fileAssembly.toBean(post.getDescription()));
    postBean.setSubPosts(subPosts);
    postBean.setContributors(contributors);
    postBean.setTags(tags);
    postBean.setCreated(created);
    postBean.setUpdated(updated);
    return postBean;
  }

  public PostBean toBeanWithoutSubPosts(Post post) {
    List<UserBean> contributors = post
      .getContributors()
      .stream()
      .map(userAssembly::toBean)
      .collect(Collectors.toList());

    List<TagBean> tags = post
      .getTags()
      .stream()
      .map(tagAssembly::toBean)
      .collect(Collectors.toList());

    String created = timeService
      .dateToString(post.getCreated(), false)
      .orElse(null);

    String updated = timeService
      .dateToString(post.getUpdated(), false)
      .orElse(null);

    PostBean postBean = new PostBean();
    postBean.setId(post.getId());
    postBean.setTitle(post.getTitle());
    postBean.setDescription(fileAssembly.toBean(post.getDescription()));
    postBean.setSubPosts(List.of());
    postBean.setContributors(contributors);
    postBean.setTags(tags);
    postBean.setCreated(created);
    postBean.setUpdated(updated);
    return postBean;
  }

}
