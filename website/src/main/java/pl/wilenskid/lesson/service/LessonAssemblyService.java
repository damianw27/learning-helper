package pl.wilenskid.lesson.service;

import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.content.bean.ContentExtendedBean;
import pl.wilenskid.content.service.ContentAssemblyService;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.lesson.bean.LessonBean;
import pl.wilenskid.lesson.bean.LessonContentBean;
import pl.wilenskid.lesson.db.LessonContentEntity;
import pl.wilenskid.lesson.db.LessonEntity;
import pl.wilenskid.section.service.SectionAssemblyService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Named
public class LessonAssemblyService {

  private final ContentService contentService;
  private final ContentAssemblyService contentAssemblyService;
  private final TimeService timeService;
  private final SectionAssemblyService sectionAssemblyService;

  @Inject
  public LessonAssemblyService(ContentService contentService,
                               ContentAssemblyService contentAssemblyService,
                               TimeService timeService,
                               SectionAssemblyService sectionAssemblyService) {
    this.contentService = contentService;
    this.contentAssemblyService = contentAssemblyService;
    this.timeService = timeService;
    this.sectionAssemblyService = sectionAssemblyService;
  }

  public LessonBean toBean(LessonEntity lessonEntity) {
    String created = timeService
      .dateToString(lessonEntity.getCreated(), false)
      .orElseThrow(IllegalStateException::new);

    LessonBean lessonBean = new LessonBean();
    lessonBean.setId(lessonEntity.getId());
    lessonBean.setTitle(lessonEntity.getTitle());
    lessonBean.setLessonContents(lessonEntity.getLessonContents().stream().map(this::toBean).collect(Collectors.toList()));
    lessonBean.setCreated(created);
    lessonBean.setSection(sectionAssemblyService.toBean(lessonEntity.getSection()));

    return lessonBean;
  }

  public LessonContentBean toBean(LessonContentEntity lessonContentEntity) {
    ContentExtendedBean content = contentService
      .getContentById(lessonContentEntity.getContentId())
      .map(contentAssemblyService::toExtendedBean)
      .orElseThrow(EntityNotFoundException::new);

    LessonContentBean lessonContentBean = new LessonContentBean();
    lessonContentBean.setId(lessonContentEntity.getId());
    lessonContentBean.setLessonId(lessonContentEntity.getLesson().getId());
    lessonContentBean.setLearningStyle(lessonContentEntity.getLearningStyle());
    lessonContentBean.setContent(content);

    return lessonContentBean;
  }

}
