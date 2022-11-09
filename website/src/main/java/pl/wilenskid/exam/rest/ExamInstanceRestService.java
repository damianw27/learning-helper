package pl.wilenskid.exam.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.wilenskid.exam.bean.ExamInstanceBean;
import pl.wilenskid.exam.bean.ExamInstanceCreateBean;
import pl.wilenskid.exam.bean.ExamInstanceUpdateBean;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.exam.db.ExamDefinitionEntity;
import pl.wilenskid.exam.db.ExamDefinitionRepository;
import pl.wilenskid.exam.db.ExamInstanceEntity;
import pl.wilenskid.exam.db.ExamInstanceRepository;
import pl.wilenskid.exam.service.ExamInstanceAssemblyService;
import pl.wilenskid.question.db.QuestionEntity;
import pl.wilenskid.question.db.QuestionRepository;
import pl.wilenskid.user.db.UserEntity;
import pl.wilenskid.user.db.UserRepository;
import pl.wilenskid.user.service.UserService;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestService
@RequestMapping("/exam-instance")
public class ExamInstanceRestService {

  private final TimeService timeService;
  private final ExamDefinitionRepository examDefinitionRepository;
  private final ExamInstanceRepository examInstanceRepository;
  private final QuestionRepository questionRepository;
  private final ExamInstanceAssemblyService examInstanceAssemblyService;
  private final UserService userService;
  private final UserRepository userRepository;

  @Inject
  public ExamInstanceRestService(TimeService timeService,
                                 ExamDefinitionRepository examDefinitionRepository,
                                 ExamInstanceRepository examInstanceRepository,
                                 QuestionRepository questionRepository,
                                 ExamInstanceAssemblyService examInstanceAssemblyService,
                                 UserService userService,
                                 UserRepository userRepository) {
    this.timeService = timeService;
    this.examDefinitionRepository = examDefinitionRepository;
    this.examInstanceRepository = examInstanceRepository;
    this.questionRepository = questionRepository;
    this.examInstanceAssemblyService = examInstanceAssemblyService;
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @ResponseBody
  @GetMapping("/{examInstanceId}")
  public ResponseEntity<ExamInstanceBean> getInstanceById(@PathVariable("examInstanceId") Long examInstanceId) {
    if (examInstanceId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    ExamInstanceEntity examInstanceEntity = examInstanceRepository
      .findById(examInstanceId)
      .orElseThrow(EntityNotFoundException::new);

    return ResponseEntity.ok(examInstanceAssemblyService.toBean(examInstanceEntity));
  }

  @ResponseBody
  @GetMapping("/current-user")
  public ResponseEntity<List<ExamInstanceBean>> getInstancesForLoggedInUser() {
    UserEntity loggedInUser = userService.getLoggedInUser();

    List<ExamInstanceBean> examInstances = StreamSupport
      .stream(examInstanceRepository.findAll().spliterator(), true)
      .filter(examInstanceEntity -> examInstanceEntity.getUserId().equals(loggedInUser.getId()))
      .map(examInstanceAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(examInstances);
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<ExamInstanceBean> createExamInstance(@RequestBody ExamInstanceCreateBean examInstanceCreateBean) {
    Calendar startDateTime = timeService
      .dateTimeFromString(examInstanceCreateBean.getStartDateTime())
      .orElseThrow(IllegalStateException::new);

    ExamDefinitionEntity examDefinitionEntity = examDefinitionRepository
      .findById(examInstanceCreateBean.getExamDefinition().getId())
      .orElseThrow(IllegalStateException::new);

    List<QuestionEntity> questionEntityList = StreamSupport.stream(questionRepository.findAll().spliterator(), false)
      .collect(Collectors.toList());

    Collections.shuffle(questionEntityList);

    String questions = questionEntityList
      .parallelStream()
      .filter(questionEntity -> questionEntity.getId() != null)
      .filter(questionEntity -> questionEntity.getCourse().getId() != null)
      .filter(questionEntity -> questionEntity.getCourse().getId().equals(examDefinitionEntity.getCourse().getId()))
      .limit(examDefinitionEntity.getQuestionsCount())
      .map(QuestionEntity::getId)
      .map(Object::toString)
      .collect(Collectors.joining(";"));

    ExamInstanceEntity examInstanceEntity = new ExamInstanceEntity();
    examInstanceEntity.setStartDateTime(startDateTime);
    examInstanceEntity.setQuestions(questions);
    examInstanceEntity.setCourseId(examDefinitionEntity.getCourse().getId());
    examInstanceEntity.setExamDefinition(examDefinitionEntity);
    examInstanceEntity.setUserId(userService.getLoggedInUser().getId());
    examInstanceEntity.setCreated(Calendar.getInstance());

    ExamInstanceEntity createdExamInstanceEntity = examInstanceRepository
      .save(examInstanceEntity);

    examDefinitionEntity.getInstances().add(createdExamInstanceEntity);
    examDefinitionRepository.save(examDefinitionEntity);

    return ResponseEntity.ok(examInstanceAssemblyService.toBean(createdExamInstanceEntity));
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<ExamInstanceBean> updateExamInstance(@RequestBody ExamInstanceUpdateBean examInstanceCreateBean) {
    ExamInstanceEntity examInstanceEntity = examInstanceRepository
      .findById(examInstanceCreateBean.getId())
      .orElseThrow(IllegalStateException::new);

    Calendar endDateTime = timeService
      .dateTimeFromString(examInstanceCreateBean.getEndDateTime())
      .orElseThrow(IllegalStateException::new);

    List<QuestionEntity> questions = Arrays.stream(examInstanceEntity.getQuestions().split(";"))
      .parallel()
      .map(Long::parseLong)
      .map(questionRepository::findById)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.toList());

    examInstanceEntity.setEndDateTime(endDateTime);
    examInstanceEntity.setAnswers(examInstanceCreateBean.getAnswers());
    examInstanceEntity.setScore(calculateScore(questions, examInstanceCreateBean.getAnswers()));

    ExamInstanceEntity updatedExamInstanceEntity = examInstanceRepository.save(examInstanceEntity);

    UserEntity loggedInUser = userService.getLoggedInUser();

    ExamDefinitionEntity examDefinition = examInstanceEntity.getExamDefinition();

    List<ExamInstanceEntity> examInstanceEntityStream = examDefinition
      .getInstances()
      .parallelStream()
      .filter(examInstanceEntity1 -> examInstanceEntity1.getUserId().equals(loggedInUser.getId()))
      .collect(Collectors.toList());

    if (examInstanceEntityStream.size() == examDefinition.getAttemptsCount()) {
      loggedInUser.setLearningStyle(null);
      userRepository.save(loggedInUser);
    }

    return ResponseEntity.ok(examInstanceAssemblyService.toBean(updatedExamInstanceEntity));
  }

  private double calculateScore(List<QuestionEntity> questions, String answers) {
    List<Long> answersIds = Arrays
      .stream(answers.trim().split(";"))
      .parallel()
      .filter(s -> !s.equals(""))
      .map(Long::parseLong)
      .collect(Collectors.toList());

    double result = 0.0d;

    for (int answerIndex = 0; answerIndex < answersIds.size(); answerIndex++) {
      Long answerId = answersIds.get(answerIndex);

      boolean isValidAnswer = questions
        .get(answerIndex)
        .getAnswers()
        .stream()
        .filter(answerEntity -> answerEntity.getId() != null)
        .anyMatch(answerEntity -> answerEntity.getId().equals(answerId) && answerEntity.getIsValid());

      if (!isValidAnswer) {
        continue;
      }

      result++;
    }

    return result / (double) questions.size();
  }

}
