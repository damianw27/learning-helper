package pl.wilenskid.course.service;

import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.content.bean.ContentExtendedBean;
import pl.wilenskid.content.service.ContentAssemblyService;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.course.bean.CourseBean;
import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.metatag.db.MetaTagEntity;
import pl.wilenskid.user.bean.UserBean;
import pl.wilenskid.user.db.UserRepository;
import pl.wilenskid.user.service.UserAssemblyService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class CourseAssemblyService {

  private final ContentService contentService;
  private final UserRepository userRepository;
  private final UserAssemblyService userAssemblyService;
  private final TimeService timeService;
  private final ContentAssemblyService contentAssemblyService;

  @Inject
  public CourseAssemblyService(ContentService contentService,
                               UserRepository userRepository,
                               UserAssemblyService userAssemblyService,
                               TimeService timeService,
                               ContentAssemblyService contentAssemblyService) {
    this.contentService = contentService;
    this.userRepository = userRepository;
    this.userAssemblyService = userAssemblyService;
    this.timeService = timeService;
    this.contentAssemblyService = contentAssemblyService;
  }

  public CourseBean toBean(CourseEntity courseEntity) {
    ContentExtendedBean description = contentService
      .getContentById(courseEntity.getDescriptionContentId())
      .map(contentAssemblyService::toExtendedBean)
      .orElseThrow(IllegalStateException::new);

    UserBean author = userRepository
      .findById(courseEntity.getAuthorId())
      .map(userAssemblyService::toBean)
      .orElseThrow(IllegalStateException::new);

    String created = timeService
      .dateToString(courseEntity.getCreated(), false)
      .orElseThrow(IllegalStateException::new);

    List<Long> sectionsIds = courseEntity
      .getSections()
      .parallelStream()
      .map(AbstractPersistable::getId)
      .collect(Collectors.toList());

    List<String> tags = courseEntity
      .getTags()
      .stream()
      .map(MetaTagEntity::getToken)
      .collect(Collectors.toList());

    CourseBean courseBean = new CourseBean();
    courseBean.setId(courseEntity.getId());
    courseBean.setTitle(courseEntity.getTitle());
    courseBean.setDescription(description);
    courseBean.setAuthor(author);
    courseBean.setCreated(created);
    courseBean.setSectionsIds(sectionsIds);
    courseBean.setTags(List.copyOf(tags));

    return courseBean;
  }

}
