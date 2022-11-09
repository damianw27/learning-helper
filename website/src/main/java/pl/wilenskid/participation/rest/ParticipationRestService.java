package pl.wilenskid.participation.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.course.db.CourseRepository;
import pl.wilenskid.participation.bean.ParticipationBean;
import pl.wilenskid.participation.db.ParticipationEntity;
import pl.wilenskid.participation.db.ParticipationRepository;
import pl.wilenskid.participation.service.ParticipationAssemblyService;
import pl.wilenskid.user.db.UserEntity;
import pl.wilenskid.user.service.UserService;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestService
@RequestMapping("/participation")
public class ParticipationRestService {

  private final CourseRepository courseRepository;
  private final ParticipationRepository participationRepository;
  private final UserService userService;
  private final ParticipationAssemblyService participationAssemblyService;

  @Inject
  public ParticipationRestService(CourseRepository courseRepository,
                                  ParticipationRepository participationRepository,
                                  UserService userService,
                                  ParticipationAssemblyService participationAssemblyService) {
    this.courseRepository = courseRepository;
    this.participationRepository = participationRepository;
    this.userService = userService;
    this.participationAssemblyService = participationAssemblyService;
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<List<ParticipationBean>> getParticipation(@PathVariable("courseId") Long courseId) {
    if (courseId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    List<ParticipationBean> participation = StreamSupport
      .stream(participationRepository.findAll().spliterator(), true)
      .filter(participationEntity -> participationEntity.getCourse() != null && participationEntity.getCourse().getId() != null)
      .filter(participationEntity -> participationEntity.getCourse().getId().equals(courseId))
      .map(participationAssemblyService::toBean)
      .collect(Collectors.toList());

    return ResponseEntity.ok(participation);
  }

  @GetMapping("/logged-user/{courseId}")
  public ResponseEntity<ParticipationBean> getParticipationForUser(@PathVariable("courseId") Long courseId) {
    if (courseId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    UserEntity loggedInUser = userService.getLoggedInUser();

    ParticipationBean participation = StreamSupport
      .stream(participationRepository.findAll().spliterator(), true)
      .filter(participationEntity -> participationEntity.getCourse() != null && participationEntity.getCourse().getId() != null)
      .filter(participationEntity -> participationEntity.getCourse().getId().equals(courseId))
      .filter(participationEntity -> canUserViewCourse(loggedInUser, participationEntity))
      .map(participationAssemblyService::toBean)
      .findFirst()
      .orElse(null);

    return ResponseEntity.ok(participation);
  }

  @PostMapping("/{courseId}")
  public ResponseEntity<ParticipationBean> createParticipation(@PathVariable("courseId") Long courseId) {
    if (courseId == null) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    CourseEntity courseEntity = courseRepository
      .findById(courseId)
      .orElseThrow(EntityNotFoundException::new);

    UserEntity loggedInUser = userService.getLoggedInUser();

    Optional<ParticipationEntity> foundParticipationEntity = participationRepository
      .findByUserIdAndCourse(loggedInUser.getId(), courseEntity);

    if (foundParticipationEntity.isPresent()) {
      return ResponseEntity.ok(participationAssemblyService.toBean(foundParticipationEntity.get()));
    }

    ParticipationEntity participationEntity = new ParticipationEntity();
    participationEntity.setUserId(loggedInUser.getId());
    participationEntity.setCourse(courseEntity);
    participationEntity.setCreated(Calendar.getInstance());
    ParticipationEntity createdParticipationEntity = participationRepository.save(participationEntity);

    courseEntity.getParticipants().add(createdParticipationEntity);
    courseRepository.save(courseEntity);

    return ResponseEntity.ok(participationAssemblyService.toBean(participationEntity));
  }

  private boolean canUserViewCourse(UserEntity loggedInUser, ParticipationEntity participationEntity) {
    return participationEntity.getUserId().equals(loggedInUser.getId())
      || participationEntity.getCourse().getAuthorId().equals(loggedInUser.getId());
  }

}
