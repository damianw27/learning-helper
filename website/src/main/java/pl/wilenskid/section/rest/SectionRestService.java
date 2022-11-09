package pl.wilenskid.section.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.content.db.ContentEntity;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.course.db.CourseRepository;
import pl.wilenskid.section.bean.SectionBean;
import pl.wilenskid.section.bean.SectionCreateBean;
import pl.wilenskid.section.bean.SectionUpdateBean;
import pl.wilenskid.section.db.SectionEntity;
import pl.wilenskid.section.db.SectionRepository;
import pl.wilenskid.section.service.SectionAssemblyService;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestService
@RequestMapping("/section")
public class SectionRestService {

  private final SectionRepository sectionRepository;
  private final CourseRepository courseRepository;
  private final ContentService contentService;
  private final SectionAssemblyService sectionAssemblyService;

  @Inject
  public SectionRestService(SectionRepository sectionRepository,
                            CourseRepository courseRepository,
                            ContentService contentService,
                            SectionAssemblyService sectionAssemblyService) {
    this.sectionRepository = sectionRepository;
    this.courseRepository = courseRepository;
    this.contentService = contentService;
    this.sectionAssemblyService = sectionAssemblyService;
  }

  @ResponseBody
  @GetMapping
  public ResponseEntity<List<SectionBean>> getSectionsByIds(@RequestParam("sectionId") List<Long> sectionsIds) {
    List<SectionBean> sections = sectionsIds
      .parallelStream()
      .map(sectionRepository::findById)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .map(sectionAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(sections);
  }

  @ResponseBody
  @GetMapping("/by-course/{courseId}")
  public ResponseEntity<List<SectionBean>> getLessonByCourseId(@PathVariable("courseId") Long courseId) {
    if (courseId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    List<SectionBean> lessons = StreamSupport.stream(sectionRepository.findAll().spliterator(), true)
      .filter(sectionEntity -> sectionEntity.getCourse() != null)
      .filter(sectionEntity -> sectionEntity.getCourse().getId() != null)
      .filter(sectionEntity -> sectionEntity.getCourse().getId().equals(courseId))
      .map(sectionAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(lessons);
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<SectionBean> createSection(@RequestBody SectionCreateBean sectionCreateBean) {
    CourseEntity courseEntity = courseRepository
      .findById(sectionCreateBean.getCourseId())
      .orElseThrow(EntityNotFoundException::new);

    Long descriptionContentId = contentService
      .uploadContent("")
      .map(ContentEntity::getId)
      .orElseThrow(IllegalStateException::new);

    SectionEntity sectionEntity = new SectionEntity();
    sectionEntity.setCourse(courseEntity);
    sectionEntity.setTitle(sectionCreateBean.getTitle());
    sectionEntity.setDescriptionContentId(descriptionContentId);
    sectionEntity.setCreated(Calendar.getInstance());
    sectionEntity.setCardinalIndex(sectionCreateBean.getCardinalIndex());
    sectionEntity.setLessons(new HashSet<>());
    SectionEntity createdSectionEntity = sectionRepository.save(sectionEntity);

    courseEntity.getSections().add(createdSectionEntity);
    courseRepository.save(courseEntity);

    return ResponseEntity.ok(sectionAssemblyService.toBean(createdSectionEntity));
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<SectionBean> updateSection(@RequestBody SectionUpdateBean sectionUpdateBean) {
    SectionEntity sectionEntity = sectionRepository
      .findById(sectionUpdateBean.getId())
      .orElseThrow(EntityNotFoundException::new);

    CourseEntity courseEntity = courseRepository
      .findById(sectionUpdateBean.getCourseId())
      .orElseThrow(EntityNotFoundException::new);

    Long descriptionContentId = contentService
      .updateContent(sectionEntity.getDescriptionContentId(), sectionUpdateBean.getDescription())
      .map(ContentEntity::getId)
      .orElseThrow(IllegalStateException::new);

    CourseEntity currentCourseEntity = sectionEntity.getCourse();

    if (!courseEntity.equals(currentCourseEntity)) {
      currentCourseEntity.getSections().remove(sectionEntity);
      courseRepository.save(currentCourseEntity);

      courseEntity.getSections().add(sectionEntity);
      courseRepository.save(courseEntity);
    }

    sectionEntity.setTitle(sectionUpdateBean.getTitle());
    sectionEntity.setCourse(courseEntity);
    sectionEntity.setDescriptionContentId(descriptionContentId);
    sectionEntity.setCardinalIndex(sectionUpdateBean.getCardinalIndex());
    sectionRepository.save(sectionEntity);

    return ResponseEntity.ok(sectionAssemblyService.toBean(sectionEntity));
  }

  @DeleteMapping("/{sectionId}")
  public void deleteCourse(@PathVariable("sectionId") Long sectionId) {
    SectionEntity sectionEntity = sectionRepository
      .findById(sectionId)
      .orElseThrow(EntityNotFoundException::new);

    CourseEntity course = sectionEntity.getCourse();
    course.getSections().remove(sectionEntity);
    courseRepository.save(course);

    sectionEntity.setCourse(null);
    sectionRepository.save(sectionEntity);

    sectionRepository.delete(sectionEntity);
  }


}
