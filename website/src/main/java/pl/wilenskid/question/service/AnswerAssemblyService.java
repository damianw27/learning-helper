package pl.wilenskid.question.service;

import pl.wilenskid.content.bean.ContentExtendedBean;
import pl.wilenskid.content.service.ContentAssemblyService;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.question.bean.AnswerBean;
import pl.wilenskid.question.db.AnswerEntity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

@Named
public class AnswerAssemblyService {

  private final ContentService contentService;
  private final ContentAssemblyService contentAssemblyService;

  @Inject
  public AnswerAssemblyService(ContentService contentService,
                               ContentAssemblyService contentAssemblyService) {
    this.contentService = contentService;
    this.contentAssemblyService = contentAssemblyService;
  }

  public AnswerBean toBean(AnswerEntity answerEntity) {
    ContentExtendedBean content = contentService
      .getContentById(answerEntity.getContentId())
      .map(contentAssemblyService::toExtendedBean)
      .orElseThrow(EntityNotFoundException::new);

    AnswerBean answerBean = new AnswerBean();
    answerBean.setId(answerEntity.getId());
    answerBean.setQuestionId(answerEntity.getQuestion().getId());
    answerBean.setContent(content);
    answerBean.setIsValid(answerEntity.getIsValid());

    return answerBean;
  }

}
