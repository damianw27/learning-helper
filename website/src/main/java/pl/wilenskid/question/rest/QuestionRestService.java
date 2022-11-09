package pl.wilenskid.question.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.question.bean.AnswerCreateBean;
import pl.wilenskid.question.bean.AnswerUpdateBean;
import pl.wilenskid.question.bean.QuestionBean;
import pl.wilenskid.question.bean.QuestionCreateBean;
import pl.wilenskid.question.bean.QuestionUpdateBean;
import pl.wilenskid.question.db.AnswerEntity;
import pl.wilenskid.question.db.QuestionEntity;
import pl.wilenskid.question.service.AnswerService;
import pl.wilenskid.question.service.QuestionAssemblyService;
import pl.wilenskid.question.service.QuestionService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RestService
@RequestMapping("/question")
public class QuestionRestService {

  private final QuestionService questionService;
  private final AnswerService answerService;
  private final QuestionAssemblyService questionAssemblyService;

  @Inject
  public QuestionRestService(QuestionService questionService,
                             AnswerService answerService,
                             QuestionAssemblyService questionAssemblyService) {
    this.questionService = questionService;
    this.answerService = answerService;
    this.questionAssemblyService = questionAssemblyService;
  }

  @ResponseBody
  @GetMapping("/{courseId}")
  public ResponseEntity<List<QuestionBean>> getQuestionsForCourse(@PathVariable("courseId") long courseId) {
    List<QuestionEntity> foundQuestions = questionService.getQuestionsForCourseById(courseId);

    List<QuestionBean> questions = foundQuestions
      .parallelStream()
      .map(questionAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(questions);
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<QuestionBean> createQuestion(@RequestBody QuestionCreateBean questionCreateBean) {
    QuestionEntity questionEntity = questionService.createAndReturnQuestion(questionCreateBean);

    for (AnswerCreateBean answerCreateBean : questionCreateBean.getAnswers()) {
      AnswerEntity answerEntity = answerService.createAndReturnAnswer(answerCreateBean, questionEntity);
      questionService.addAnswerToQuestion(questionEntity, answerEntity);
    }

    return ResponseEntity.ok(questionAssemblyService.toBean(questionEntity));
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<QuestionBean> updateQuestion(@RequestBody QuestionUpdateBean questionUpdateBean) {
    QuestionEntity questionEntity = questionService.updateAndReturnQuestion(questionUpdateBean);

    questionUpdateBean
      .getAnswers()
      .parallelStream()
      .filter(answerUpdateBean -> answerUpdateBean.getId() != -1)
      .forEach(answerUpdateBean -> answerService.updateAndReturnAnswer(answerUpdateBean, questionEntity));

    questionUpdateBean
      .getAnswers()
      .parallelStream()
      .filter(answerUpdateBean -> answerUpdateBean.getId() == -1)
      .map(this::getCreateBeanFromUpdateBean)
      .map(answerCreateBean -> answerService.createAndReturnAnswer(answerCreateBean, questionEntity))
      .forEach(answerEntity -> questionService.addAnswerToQuestion(questionEntity, answerEntity));

    return ResponseEntity.ok(questionAssemblyService.toBean(questionEntity));
  }

  private AnswerCreateBean getCreateBeanFromUpdateBean(AnswerUpdateBean answerUpdateBean) {
    AnswerCreateBean answerCreateBean = new AnswerCreateBean();
    answerCreateBean.setIsValid(answerUpdateBean.getIsValid());
    answerCreateBean.setContent(answerUpdateBean.getContent());
    return answerCreateBean;
  }

}
