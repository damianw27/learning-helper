package pl.wilenskid.section.service;

import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.content.bean.ContentExtendedBean;
import pl.wilenskid.content.service.ContentAssemblyService;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.section.bean.SectionBean;
import pl.wilenskid.section.db.SectionEntity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

@Named
public class SectionAssemblyService {

  private final ContentService contentService;
  private final ContentAssemblyService contentAssemblyService;
  private final TimeService timeService;

  @Inject
  public SectionAssemblyService(ContentService contentService,
                                ContentAssemblyService contentAssemblyService,
                                TimeService timeService) {
    this.contentService = contentService;
    this.contentAssemblyService = contentAssemblyService;
    this.timeService = timeService;
  }

  public SectionBean toBean(SectionEntity sectionEntity) {
    ContentExtendedBean description = contentService
      .getContentById(sectionEntity.getDescriptionContentId())
      .map(contentAssemblyService::toExtendedBean)
      .orElseThrow(EntityNotFoundException::new);

    String created = timeService
      .dateToString(sectionEntity.getCreated(), false)
      .orElseThrow(IllegalStateException::new);

    SectionBean sectionBean = new SectionBean();
    sectionBean.setId(sectionEntity.getId());
    sectionBean.setCourseId(sectionEntity.getCourse().getId());
    sectionBean.setTitle(sectionEntity.getTitle());
    sectionBean.setDescription(description);
    sectionBean.setCardinalIndex(sectionEntity.getCardinalIndex());
    sectionBean.setCreated(created);

    return sectionBean;
  }

}
