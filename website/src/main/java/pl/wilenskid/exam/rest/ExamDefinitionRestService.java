package pl.wilenskid.exam.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.wilenskid.exam.bean.ExamDefinitionBean;
import pl.wilenskid.exam.bean.ExamDefinitionCreateBean;
import pl.wilenskid.exam.bean.ExamDefinitionUpdateBean;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.exam.db.ExamDefinitionEntity;
import pl.wilenskid.exam.db.ExamDefinitionRepository;
import pl.wilenskid.exam.db.ExamInstanceRepository;
import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.course.db.CourseRepository;
import pl.wilenskid.exam.service.ExamDefinitionAssemblyService;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestService
@RequestMapping("/exam-definition")
public class ExamDefinitionRestService {

  private final ExamDefinitionRepository examDefinitionRepository;
  private final ExamInstanceRepository examInstanceRepository;
  private final CourseRepository courseRepository;
  private final ExamDefinitionAssemblyService examDefinitionAssemblyService;
  private final TimeService timeService;

  public ExamDefinitionRestService(ExamDefinitionRepository examDefinitionRepository,
                                   ExamInstanceRepository examInstanceRepository,
                                   CourseRepository courseRepository,
                                   ExamDefinitionAssemblyService examDefinitionAssemblyService,
                                   TimeService timeService) {
    this.examDefinitionRepository = examDefinitionRepository;
    this.examInstanceRepository = examInstanceRepository;
    this.courseRepository = courseRepository;
    this.examDefinitionAssemblyService = examDefinitionAssemblyService;
    this.timeService = timeService;
  }

  @ResponseBody
  @GetMapping("/{examDefinitionId}")
  public ResponseEntity<ExamDefinitionBean> getDefinitionById(@PathVariable("examDefinitionId") Long examDefinitionId) {
    if (examDefinitionId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    ExamDefinitionEntity examDefinitionEntity = examDefinitionRepository
      .findById(examDefinitionId)
      .orElseThrow(EntityNotFoundException::new);

    return ResponseEntity.ok(examDefinitionAssemblyService.toBean(examDefinitionEntity));
  }

  @ResponseBody
  @GetMapping("/by-course/{courseId}")
  public ResponseEntity<List<ExamDefinitionBean>> getByCourseId(@PathVariable("courseId") Long courseId) {
    if (courseId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    Calendar currentDate = Calendar.getInstance();

    List<ExamDefinitionBean> foundExamDefinitions = StreamSupport.stream(examDefinitionRepository.findAll().spliterator(), true)
      .filter(examDefinitionEntity -> examDefinitionEntity.getCourse().getId() != null)
      .filter(examDefinitionEntity -> examDefinitionEntity.getCourse().getId().equals(courseId))
      .filter(examDefinitionEntity -> examDefinitionEntity.getStartDateTime().before(currentDate) || examDefinitionEntity.getStartDateTime().equals(currentDate))
      .map(examDefinitionAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(foundExamDefinitions);
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<ExamDefinitionBean> createExamDefinition(@RequestBody ExamDefinitionCreateBean examDefinitionCreateBean) {
    Calendar startDateTime = timeService
      .dateTimeFromString(examDefinitionCreateBean.getStartDateTime())
      .orElseThrow(IllegalStateException::new);

    Calendar endDateTime = timeService
      .dateTimeFromString(examDefinitionCreateBean.getEndDateTime())
      .orElseThrow(IllegalStateException::new);

    CourseEntity courseEntity = courseRepository
      .findById(examDefinitionCreateBean.getCourseId())
      .orElseThrow(IllegalStateException::new);

    ExamDefinitionEntity examDefinitionEntity = new ExamDefinitionEntity();
    examDefinitionEntity.setPassLevel(examDefinitionCreateBean.getPassLevel());
    examDefinitionEntity.setQuestionsCount(examDefinitionCreateBean.getQuestionsCount());
    examDefinitionEntity.setAttemptsCount(examDefinitionCreateBean.getAttemptsCount());
    examDefinitionEntity.setStartDateTime(startDateTime);
    examDefinitionEntity.setEndDateTime(endDateTime);
    examDefinitionEntity.setCourse(courseEntity);
    examDefinitionEntity.setInstances(new HashSet<>());

    ExamDefinitionEntity createdExamDefinitionEntity = examDefinitionRepository
      .save(examDefinitionEntity);

    return ResponseEntity.ok(examDefinitionAssemblyService.toBean(createdExamDefinitionEntity));
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<ExamDefinitionBean> updateExamDefinition(@RequestBody ExamDefinitionUpdateBean examDefinitionUpdateBean) {
    Calendar startDateTime = timeService
      .dateTimeFromString(examDefinitionUpdateBean.getStartDateTime())
      .orElseThrow(IllegalStateException::new);

    Calendar endDateTime = timeService
      .dateTimeFromString(examDefinitionUpdateBean.getEndDateTime())
      .orElseThrow(IllegalStateException::new);

    ExamDefinitionEntity examDefinitionEntity = new ExamDefinitionEntity();
    examDefinitionEntity.setPassLevel(examDefinitionUpdateBean.getPassLevel());
    examDefinitionEntity.setQuestionsCount(examDefinitionUpdateBean.getQuestionsCount());
    examDefinitionEntity.setAttemptsCount(examDefinitionUpdateBean.getAttemptsCount());
    examDefinitionEntity.setStartDateTime(startDateTime);
    examDefinitionEntity.setEndDateTime(endDateTime);

    ExamDefinitionEntity updatedExamDefinitionEntity = examDefinitionRepository
      .save(examDefinitionEntity);

    return ResponseEntity.ok(examDefinitionAssemblyService.toBean(updatedExamDefinitionEntity));
  }

  @DeleteMapping("/{examDefinitionId}")
  public void removeExamDefinition(@PathVariable("examDefinitionId") Long examDefinitionId) {
    if (examDefinitionId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    ExamDefinitionEntity examDefinitionEntity = examDefinitionRepository
      .findById(examDefinitionId)
      .orElseThrow(IllegalStateException::new);

    examInstanceRepository.deleteAll(examDefinitionEntity.getInstances());
    examDefinitionRepository.delete(examDefinitionEntity);
  }

}
