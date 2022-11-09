package pl.wilenskid.lesson.rest;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.content.db.ContentEntity;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.examinator.lesson.bean.*;
import pl.wilenskid.lesson.bean.*;
import pl.wilenskid.lesson.db.LessonContentEntity;
import pl.wilenskid.lesson.db.LessonContentRepository;
import pl.wilenskid.lesson.db.LessonEntity;
import pl.wilenskid.lesson.db.LessonRepository;
import pl.wilenskid.lesson.service.LessonAssemblyService;
import pl.wilenskid.section.db.SectionEntity;
import pl.wilenskid.section.db.SectionRepository;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestService
@RequestMapping("/lesson")
public class LessonRestService {

  private final LessonRepository lessonRepository;
  private final LessonContentRepository lessonContentRepository;
  private final LessonAssemblyService lessonAssemblyService;
  private final ContentService contentService;
  private final SectionRepository sectionRepository;

  @Inject
  public LessonRestService(LessonRepository lessonRepository,
                           LessonContentRepository lessonContentRepository,
                           LessonAssemblyService lessonAssemblyService,
                           ContentService contentService,
                           SectionRepository sectionRepository) {
    this.lessonRepository = lessonRepository;
    this.lessonContentRepository = lessonContentRepository;
    this.lessonAssemblyService = lessonAssemblyService;
    this.contentService = contentService;
    this.sectionRepository = sectionRepository;
  }

  @ResponseBody
  @GetMapping("/{lessonId}")
  public ResponseEntity<LessonBean> getLessonById(@PathVariable("lessonId") Long lessonId) {
    LessonEntity lessonEntity = lessonRepository
      .findById(lessonId)
      .orElseThrow(EntityNotFoundException::new);

    return ResponseEntity.ok(lessonAssemblyService.toBean(lessonEntity));
  }

  @ResponseBody
  @GetMapping("/by-section/{sectionId}")
  public ResponseEntity<List<LessonBean>> getLessonByCourseId(@PathVariable("sectionId") Long sectionId) {
    if (sectionId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    List<LessonBean> lessons = StreamSupport.stream(lessonRepository.findAll().spliterator(), true)
      .filter(lessonEntity -> lessonEntity.getSection().getId() != null)
      .filter(lessonEntity -> lessonEntity.getSection().getId().equals(sectionId))
      .map(lessonAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(lessons);
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<LessonBean> createLesson(@RequestBody LessonCreateBean lessonCreateBean) {
    SectionEntity sectionEntity = sectionRepository
      .findById(lessonCreateBean.getSectionId())
      .orElseThrow(EntityNotFoundException::new);

    LessonEntity lessonEntity = new LessonEntity();
    lessonEntity.setTitle(lessonCreateBean.getTitle());
    lessonEntity.setSection(sectionEntity);
    lessonEntity.setCardinalNumber(0);
    lessonEntity.setCreated(Calendar.getInstance());
    lessonEntity.setLessonContents(new HashSet<>());
    LessonEntity savedLessonEntity = lessonRepository.save(lessonEntity);

    return ResponseEntity.ok(lessonAssemblyService.toBean(savedLessonEntity));
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<LessonBean> updateLesson(@RequestBody LessonUpdateBean lessonUpdateBean) {
    LessonEntity lessonEntity = lessonRepository
      .findById(lessonUpdateBean.getId())
      .orElseThrow(EntityNotFoundException::new);

    SectionEntity sectionEntity = sectionRepository
      .findById(lessonUpdateBean.getSectionId())
      .orElseThrow(EntityNotFoundException::new);

    SectionEntity currentSectionEntity = lessonEntity.getSection();

    if (!sectionEntity.equals(currentSectionEntity)) {
      currentSectionEntity.getLessons().remove(lessonEntity);
      sectionRepository.save(currentSectionEntity);

      sectionEntity.getLessons().add(lessonEntity);
      sectionRepository.save(sectionEntity);
    }

    lessonEntity.setTitle(lessonUpdateBean.getTitle());
    lessonEntity.setSection(sectionEntity);
    lessonEntity.setCardinalNumber(lessonUpdateBean.getCardinalNumber());

    lessonUpdateBean.getLessonContents()
      .stream()
      .filter(lessonContentUpdateBean -> lessonContentUpdateBean.getId() == -1)
      .map(this::lessonContentCreateBeanFromUpdateBean)
      .forEach(lessonContentCreateBean -> createLessonContentAndAddToLesson(lessonContentCreateBean, lessonEntity));

    lessonUpdateBean.getLessonContents()
      .stream()
      .filter(lessonContentUpdateBean -> lessonContentUpdateBean.getId() != -1)
      .forEach(lessonContentUpdateBean -> updateLessonContent(lessonEntity, lessonContentUpdateBean));


    lessonRepository.save(lessonEntity);

    return ResponseEntity.ok(lessonAssemblyService.toBean(lessonEntity));
  }

  private void updateLessonContent(LessonEntity lessonEntity, LessonContentUpdateBean lessonContentUpdateBean) {
    LessonContentEntity lessonContentEntity = lessonContentRepository
      .findById(lessonContentUpdateBean.getId())
      .orElseThrow(IllegalStateException::new);

    ContentEntity contentEntity = contentService
      .updateContent(lessonContentEntity.getContentId(), lessonContentUpdateBean.getContent())
      .orElseThrow(IllegalStateException::new);

    lessonContentEntity.setLesson(lessonEntity);
    lessonContentEntity.setContentId(contentEntity.getId());
    lessonContentEntity.setLearningStyle(lessonContentUpdateBean.getLearningStyle());
    lessonContentRepository.save(lessonContentEntity);
  }

  private void createLessonContentAndAddToLesson(LessonContentCreateBean lessonContentCreateBean, LessonEntity lessonEntity) {
    Long contentId = contentService
      .uploadContent(lessonContentCreateBean.getContent())
      .map(AbstractPersistable::getId)
      .orElseThrow(IllegalStateException::new);

    LessonContentEntity lessonContentEntity = new LessonContentEntity();
    lessonContentEntity.setLesson(lessonEntity);
    lessonContentEntity.setContentId(contentId);
    lessonContentEntity.setLearningStyle(lessonContentCreateBean.getLearningStyle());
    LessonContentEntity savedLessonContent = lessonContentRepository.save(lessonContentEntity);
    lessonEntity.getLessonContents().add(savedLessonContent);
  }

  private LessonContentCreateBean lessonContentCreateBeanFromUpdateBean(LessonContentUpdateBean lessonContentUpdateBean) {
    LessonContentCreateBean lessonContentCreateBean = new LessonContentCreateBean();
    lessonContentCreateBean.setLearningStyle(lessonContentUpdateBean.getLearningStyle());
    lessonContentCreateBean.setContent(lessonContentUpdateBean.getContent());
    return lessonContentCreateBean;
  }

  @DeleteMapping("/{lessonId}")
  public void deleteLesson(@PathVariable("lessonId") Long lessonId) {
    lessonRepository
      .findById(lessonId)
      .ifPresent(lessonRepository::delete);
  }

  @DeleteMapping("/lesson-content/{lessonContentId}")
  public void deleteLessonContent(@PathVariable("lessonContentId") Long lessonContentId) {
    lessonContentRepository
      .findById(lessonContentId)
      .ifPresent(lessonContentRepository::delete);
  }

}
