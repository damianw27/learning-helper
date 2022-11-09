package pl.wilenskid.question.service;

import pl.wilenskid.content.db.ContentEntity;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.question.bean.AnswerCreateBean;
import pl.wilenskid.question.bean.AnswerUpdateBean;
import pl.wilenskid.question.db.AnswerEntity;
import pl.wilenskid.question.db.AnswerRepository;
import pl.wilenskid.question.db.QuestionEntity;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AnswerService {

  private final AnswerRepository answerRepository;
  private final ContentService contentService;

  @Inject
  public AnswerService(AnswerRepository answerRepository,
                       ContentService contentService) {
    this.answerRepository = answerRepository;
    this.contentService = contentService;
  }

  public AnswerEntity createAndReturnAnswer(AnswerCreateBean answerCreateBean, QuestionEntity questionEntity) {
    Long answerContentId = contentService
      .uploadContent(answerCreateBean.getContent())
      .map(ContentEntity::getId)
      .orElseThrow(IllegalStateException::new);

    AnswerEntity answerEntity = new AnswerEntity();
    answerEntity.setQuestion(questionEntity);
    answerEntity.setContentId(answerContentId);
    answerEntity.setIsValid(answerCreateBean.getIsValid());

    return answerRepository.save(answerEntity);
  }

  public AnswerEntity updateAndReturnAnswer(AnswerUpdateBean answerUpdateBean, QuestionEntity question) {
    AnswerEntity answerEntity = answerRepository
      .findById(answerUpdateBean.getId())
      .orElseThrow(IllegalStateException::new);

    ContentEntity contentEntity = contentService
      .updateContent(answerEntity.getContentId(), answerUpdateBean.getContent())
      .orElseThrow(IllegalStateException::new);

    answerEntity.setContentId(contentEntity.getId());
    answerEntity.setIsValid(answerUpdateBean.getIsValid());

    return answerRepository.save(answerEntity);
  }

}
