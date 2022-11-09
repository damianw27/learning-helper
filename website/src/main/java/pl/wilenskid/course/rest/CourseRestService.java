package pl.wilenskid.course.rest;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.content.db.ContentEntity;
import pl.wilenskid.content.service.ContentService;
import pl.wilenskid.course.bean.CourseBean;
import pl.wilenskid.course.bean.CourseUpdateBean;
import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.course.db.CourseRepository;
import pl.wilenskid.course.service.CourseAssemblyService;
import pl.wilenskid.course.bean.CourseCreateBean;
import pl.wilenskid.metatag.db.MetaTagEntity;
import pl.wilenskid.metatag.db.MetaTagRepository;
import pl.wilenskid.metatag.service.MetaTagService;
import pl.wilenskid.participation.db.ParticipationEntity;
import pl.wilenskid.participation.db.ParticipationRepository;
import pl.wilenskid.section.db.SectionEntity;
import pl.wilenskid.section.db.SectionRepository;
import pl.wilenskid.user.db.UserEntity;
import pl.wilenskid.user.service.UserService;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.QueryParam;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestService
@RequestMapping("/course")
public class CourseRestService {

  private final CourseRepository courseRepository;
  private final ParticipationRepository participationRepository;
  private final ContentService contentService;
  private final UserService userService;
  private final CourseAssemblyService courseAssemblyService;
  private final MetaTagService metaTagService;
  private final SectionRepository sectionRepository;
  private final MetaTagRepository metaTagRepository;

  @Inject
  public CourseRestService(CourseRepository courseRepository,
                           ParticipationRepository participationRepository,
                           ContentService contentService,
                           UserService userService,
                           CourseAssemblyService courseAssemblyService,
                           MetaTagService metaTagService,
                           SectionRepository sectionRepository,
                           MetaTagRepository metaTagRepository) {
    this.courseRepository = courseRepository;
    this.participationRepository = participationRepository;
    this.contentService = contentService;
    this.userService = userService;
    this.courseAssemblyService = courseAssemblyService;
    this.metaTagService = metaTagService;
    this.sectionRepository = sectionRepository;
    this.metaTagRepository = metaTagRepository;
  }

  @ResponseBody
  @GetMapping("/all/pages-count/{size}")
  public ResponseEntity<Integer> getCoursesPageCount(@PathVariable("size") Integer size) {
    int allEntriesCount = (int) courseRepository.findAll().spliterator().estimateSize();
    int pagesCount = (int) Math.ceil(allEntriesCount / (double) size);
    return ResponseEntity.ok(pagesCount);
  }

  @ResponseBody
  @GetMapping("/all")
  public ResponseEntity<List<CourseBean>> getAllCourses(@RequestParam("index") Integer index,
                                                        @RequestParam("size") Integer size) {
    Iterable<CourseEntity> foundCourses = courseRepository.findAll();

    List<CourseBean> courses = StreamSupport
      .stream(foundCourses.spliterator(), true)
      .skip((long) index * size)
      .limit(size)
      .map(courseAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(courses);
  }

  @ResponseBody
  @GetMapping("/search")
  public ResponseEntity<List<CourseBean>> searchCourses(@QueryParam("query") String query) {
    List<String> tokens = Stream.of(query.split(" "))
      .map(token -> token.toUpperCase(Locale.ROOT))
      .collect(Collectors.toList());

    Spliterator<CourseEntity> courses = courseRepository.findAll().spliterator();

    if (query.equals("")) {
      List<CourseBean> resultCourses = StreamSupport.stream(courses, true)
        .map(courseAssemblyService::toBean)
        .collect(Collectors.toList());

      return ResponseEntity.ok(resultCourses);
    }

    List<CourseEntity> tagBased = StreamSupport.stream(courses, true)
      .filter(courseEntity -> courseEntity.getTags().stream().anyMatch(metaTagEntity -> tokens.contains(metaTagEntity.getToken().toUpperCase(Locale.ROOT))))
      .collect(Collectors.toList());

    List<CourseEntity> titleBased = StreamSupport.stream(courses, true)
      .filter(courseEntity -> tokens.stream().anyMatch(tag -> courseEntity.getTitle().toUpperCase(Locale.ROOT).contains(tag)))
      .collect(Collectors.toList());

    List<CourseEntity> foundEntities = new ArrayList<>();
    foundEntities.addAll(tagBased);
    foundEntities.addAll(titleBased);

    List<CourseBean> resultCourses = foundEntities
      .stream()
      .distinct()
      .map(courseAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(resultCourses);
  }

  @ResponseBody
  @GetMapping("/{courseId}")
  public ResponseEntity<CourseBean> getCourseById(@PathVariable("courseId") Long courseId) {
    CourseEntity courseEntity = courseRepository
      .findById(courseId)
      .orElseThrow(EntityNotFoundException::new);

    return ResponseEntity.ok(courseAssemblyService.toBean(courseEntity));
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<CourseBean> createCourse(@RequestBody CourseCreateBean courseCreateBean) throws FileUploadException {
    Long descriptionContentId = contentService
      .uploadContent(courseCreateBean.getDescription())
      .map(AbstractPersistable::getId)
      .orElseThrow(FileUploadException::new);

    UserEntity loggedInUser = userService.getLoggedInUser();

    CourseEntity courseEntity = new CourseEntity();
    courseEntity.setTitle(courseCreateBean.getTitle());
    courseEntity.setDescriptionContentId(descriptionContentId);
    courseEntity.setAuthorId(loggedInUser.getId());
    courseEntity.setCreated(Calendar.getInstance());
    courseEntity.setSections(new HashSet<>());
    courseEntity.setTags(new HashSet<>());
    CourseEntity createdCourseEntity = courseRepository.save(courseEntity);

    ParticipationEntity participationEntity = new ParticipationEntity();
    participationEntity.setUserId(loggedInUser.getId());
    participationEntity.setCourse(createdCourseEntity);
    participationEntity.setCreated(Calendar.getInstance());
    participationRepository.save(participationEntity);

    return ResponseEntity.ok(courseAssemblyService.toBean(createdCourseEntity));
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<CourseBean> updateCourse(@RequestBody CourseUpdateBean courseUpdateBean) {
    CourseEntity courseEntity = courseRepository
      .findById(courseUpdateBean.getId())
      .orElseThrow(EntityNotFoundException::new);

    Long descriptionContentId = contentService
      .updateContent(courseEntity.getDescriptionContentId(), courseUpdateBean.getDescription())
      .map(ContentEntity::getId)
      .orElseThrow(IllegalStateException::new);

    courseEntity.setTitle(courseUpdateBean.getTitle());
    courseEntity.setDescriptionContentId(descriptionContentId);
    CourseEntity updatedCourse = courseRepository.save(courseEntity);

    metaTagService.createMetaTags(courseUpdateBean.getTags(), updatedCourse);

    return ResponseEntity.ok(courseAssemblyService.toBean(courseEntity));
  }

  @DeleteMapping("/{courseId}")
  public void deleteCourse(@PathVariable("courseId") Long courseId) {
    CourseEntity courseEntity = courseRepository
      .findById(courseId)
      .orElseThrow(EntityNotFoundException::new);

    Set<SectionEntity> sections = courseEntity.getSections();
    sections.forEach(sectionEntity -> sectionEntity.setCourse(null));
    sectionRepository.saveAll(sections);
    sectionRepository.deleteAll(sections);


    Set<ParticipationEntity> participants = courseEntity.getParticipants();
    participants.forEach(participationEntity -> participationEntity.setCourse(null));
    participationRepository.saveAll(participants);
    participationRepository.deleteAll(participants);

    Set<MetaTagEntity> tags = courseEntity.getTags();
    tags.forEach(metaTagEntity -> metaTagEntity.setCourse(null));
    metaTagRepository.saveAll(tags);
    metaTagRepository.deleteAll(tags);

    courseEntity.getSections().clear();
    courseEntity.getParticipants().clear();
    courseEntity.getTags().clear();
    courseRepository.save(courseEntity);

    courseRepository.delete(courseEntity);
  }

}
