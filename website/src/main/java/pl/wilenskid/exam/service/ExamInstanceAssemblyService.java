package pl.wilenskid.exam.service;

import pl.wilenskid.exam.bean.ExamInstanceBean;
import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.exam.db.ExamInstanceEntity;
import pl.wilenskid.question.bean.QuestionBean;
import pl.wilenskid.question.db.QuestionRepository;
import pl.wilenskid.question.service.QuestionAssemblyService;
import pl.wilenskid.user.bean.UserBean;
import pl.wilenskid.user.db.UserRepository;
import pl.wilenskid.user.service.UserAssemblyService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
public class ExamInstanceAssemblyService {

  private final TimeService timeService;
  private final UserRepository userRepository;
  private final UserAssemblyService userAssemblyService;
  private final QuestionRepository questionRepository;
  private final QuestionAssemblyService questionAssemblyService;

  @Inject
  public ExamInstanceAssemblyService(TimeService timeService,
                                     UserRepository userRepository,
                                     UserAssemblyService userAssemblyService,
                                     QuestionRepository questionRepository,
                                     QuestionAssemblyService questionAssemblyService) {
    this.timeService = timeService;
    this.userRepository = userRepository;
    this.userAssemblyService = userAssemblyService;
    this.questionRepository = questionRepository;
    this.questionAssemblyService = questionAssemblyService;
  }

  public ExamInstanceBean toBean(ExamInstanceEntity examInstanceEntity) {
    UserBean user = userRepository
      .findById(examInstanceEntity.getUserId())
      .map(userAssemblyService::toBean)
      .orElseThrow(EntityNotFoundException::new);

    String startDateTime = timeService
      .dateToString(examInstanceEntity.getStartDateTime(), true)
      .orElseThrow(IllegalStateException::new);

    String endDateTime = timeService
      .dateToString(examInstanceEntity.getEndDateTime(), true)
      .orElse(null);

    String created = timeService
      .dateToString(examInstanceEntity.getCreated(), true)
      .orElseThrow(IllegalStateException::new);

    List<QuestionBean> questions = Arrays
      .stream(examInstanceEntity.getQuestions().split(";"))
      .parallel()
      .map(Long::parseLong)
      .map(questionRepository::findById)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .map(questionAssemblyService::toBean)
      .collect(Collectors.toList());

    ExamInstanceBean examInstanceBean = new ExamInstanceBean();
    examInstanceBean.setId(examInstanceEntity.getId());
    examInstanceBean.setUser(user);
    examInstanceBean.setStartDateTime(startDateTime);
    examInstanceBean.setEndDateTime(endDateTime);
    examInstanceBean.setQuestions(questions);
    examInstanceBean.setAnswers(examInstanceEntity.getAnswers());
    examInstanceBean.setScore(examInstanceEntity.getScore());
    examInstanceBean.setCourseId(examInstanceEntity.getCourseId());
    examInstanceBean.setExamDefinitionId(examInstanceEntity.getExamDefinition().getId());
    examInstanceBean.setCreated(created);

    return examInstanceBean;
  }

}
