package pl.wilenskid.question.service;

import pl.wilenskid.content.bean.ContentExtendedBean;
import pl.wilenskid.content.service.ContentAssemblyService;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.question.bean.AnswerBean;
import pl.wilenskid.question.bean.QuestionBean;
import pl.wilenskid.question.db.QuestionEntity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Named
@Transactional
public class QuestionAssemblyService {

  private final ContentService contentService;
  private final ContentAssemblyService contentAssemblyService;
  private final AnswerAssemblyService answerAssemblyService;

  @Inject
  public QuestionAssemblyService(ContentService contentService,
                                 ContentAssemblyService contentAssemblyService,
                                 AnswerAssemblyService answerAssemblyService) {
    this.contentService = contentService;
    this.contentAssemblyService = contentAssemblyService;
    this.answerAssemblyService = answerAssemblyService;
  }

  public QuestionBean toBean(QuestionEntity questionEntity) {
    ContentExtendedBean content = contentService
      .getContentById(questionEntity.getContentId())
      .map(contentAssemblyService::toExtendedBean)
      .orElseThrow(EntityNotFoundException::new);

    List<AnswerBean> answers = questionEntity
      .getAnswers()
      .stream()
      .map(answerAssemblyService::toBean)
      .collect(Collectors.toList());

    QuestionBean questionBean = new QuestionBean();
    questionBean.setId(questionEntity.getId());
    questionBean.setCourseId(questionEntity.getCourse().getId());
    questionBean.setContent(content);
    questionBean.setAnswers(answers);

    return questionBean;
  }

}
