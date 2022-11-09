package pl.wilenskid.question.service;

import pl.wilenskid.content.db.ContentEntity;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.course.db.CourseRepository;
import pl.wilenskid.question.bean.QuestionCreateBean;
import pl.wilenskid.question.bean.QuestionUpdateBean;
import pl.wilenskid.question.db.AnswerEntity;
import pl.wilenskid.question.db.QuestionEntity;
import pl.wilenskid.question.db.QuestionRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class QuestionService {

  private final QuestionRepository questionRepository;
  private final CourseRepository courseRepository;
  private final ContentService contentService;

  @Inject
  public QuestionService(QuestionRepository questionRepository,
                         CourseRepository courseRepository,
                         ContentService contentService) {
    this.questionRepository = questionRepository;
    this.courseRepository = courseRepository;
    this.contentService = contentService;
  }

  public QuestionEntity createAndReturnQuestion(QuestionCreateBean questionCreateBean) {
    CourseEntity courseEntity = courseRepository
      .findById(questionCreateBean.getCourseId())
      .orElseThrow(EntityNotFoundException::new);

    Long contentId = contentService
      .uploadContent(questionCreateBean.getContent())
      .map(ContentEntity::getId)
      .orElseThrow(IllegalStateException::new);

    QuestionEntity questionEntity = new QuestionEntity();
    questionEntity.setCourse(courseEntity);
    questionEntity.setContentId(contentId);
    questionEntity.setAnswers(new HashSet<>());

    return questionRepository.save(questionEntity);
  }

  public void addAnswerToQuestion(QuestionEntity questionEntity, AnswerEntity answerEntity) {
    questionEntity.getAnswers().add(answerEntity);
    questionRepository.save(questionEntity);
  }

  public QuestionEntity updateAndReturnQuestion(QuestionUpdateBean questionUpdateBean) {
    QuestionEntity questionEntity = questionRepository
      .findById(questionUpdateBean.getId())
      .orElseThrow(EntityNotFoundException::new);

    Long contentId = contentService
      .updateContent(questionEntity.getContentId(), questionUpdateBean.getContent())
      .map(ContentEntity::getId)
      .orElseThrow(IllegalStateException::new);

    questionEntity.setContentId(contentId);

    return questionRepository.save(questionEntity);
  }

  public List<QuestionEntity> getQuestionsForCourseById(Long courseId) {
    return StreamSupport.stream(questionRepository.findAll().spliterator(), true)
      .filter(questionEntity -> Objects.equals(questionEntity.getCourse().getId(), courseId))
      .collect(Collectors.toList());
  }

}
