package pl.wilenskid.content.service;

import pl.wilenskid.content.bean.ContentBean;
import pl.wilenskid.content.bean.ContentExtendedBean;
import pl.wilenskid.content.db.ContentEntity;
import pl.wilenskid.user.service.UserAssemblyService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ContentAssemblyService {

  private final UserAssemblyService userAssemblyService;
  private final ContentService contentService;

  @Inject
  public ContentAssemblyService(UserAssemblyService userAssemblyService,
                                ContentService contentService) {
    this.userAssemblyService = userAssemblyService;
    this.contentService = contentService;
  }

  public ContentBean toBean(ContentEntity contentEntity) {
    ContentBean contentBean = new ContentBean();
    contentBean.setName(contentEntity.getName());
    contentBean.setUrl(contentEntity.getUrl());
    contentBean.setOwner(userAssemblyService.toBean(contentEntity.getOwner()));
    return contentBean;
  }

  public ContentExtendedBean toExtendedBean(ContentEntity contentEntity) {
    ContentExtendedBean contentExtendedBean = new ContentExtendedBean();
    contentExtendedBean.setName(contentEntity.getName());
    contentExtendedBean.setUrl(contentEntity.getUrl());
//    contentExtendedBean.setOwner(userAssemblyService.toBean(contentEntity.getOwner()));
    contentExtendedBean.setOwner(null);
    contentExtendedBean.setContent(contentService.loadContentAsString(contentEntity.getName()));
    return contentExtendedBean;
  }

}
