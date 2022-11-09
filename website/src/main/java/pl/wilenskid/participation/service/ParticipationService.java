package pl.wilenskid.participation.service;

import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.participation.db.ParticipationEntity;
import pl.wilenskid.participation.db.ParticipationRepository;
import pl.wilenskid.user.db.UserEntity;
import pl.wilenskid.user.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class ParticipationService {

  private final UserService userService;
  private final ParticipationRepository participationRepository;

  @Inject
  public ParticipationService(UserService userService,
                              ParticipationRepository participationRepository) {
    this.userService = userService;
    this.participationRepository = participationRepository;
  }

  public boolean isLoggedUserParticipationToCourse(CourseEntity courseEntity) {
    UserEntity loggedInUser = userService.getLoggedInUser();

    return courseEntity
      .getParticipants()
      .parallelStream()
      .anyMatch(participationEntity -> participationEntity.getUserId().equals(loggedInUser.getId()));
  }

  public List<ParticipationEntity> getAllCoursesLoggedUserParticipate() {
    UserEntity loggedInUser = userService.getLoggedInUser();

    return StreamSupport
      .stream(participationRepository.findAll().spliterator(), true)
      .filter(participationEntity -> participationEntity.getUserId().equals(loggedInUser.getId()))
      .collect(Collectors.toList());
  }
}
