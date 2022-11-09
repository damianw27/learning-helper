package pl.wilenskid.participation.service;

import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.course.bean.CourseBean;
import pl.wilenskid.course.db.CourseRepository;
import pl.wilenskid.course.service.CourseAssemblyService;
import pl.wilenskid.participation.bean.ParticipationBean;
import pl.wilenskid.participation.db.ParticipationEntity;
import pl.wilenskid.user.bean.UserBean;
import pl.wilenskid.user.db.UserRepository;
import pl.wilenskid.user.service.UserAssemblyService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ParticipationAssemblyService {

  private final TimeService timeService;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final UserAssemblyService userAssemblyService;
  private final CourseAssemblyService courseAssemblyService;

  @Inject
  public ParticipationAssemblyService(TimeService timeService,
                                      UserRepository userRepository,
                                      CourseRepository courseRepository,
                                      UserAssemblyService userAssemblyService,
                                      CourseAssemblyService courseAssemblyService) {
    this.timeService = timeService;
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.userAssemblyService = userAssemblyService;
    this.courseAssemblyService = courseAssemblyService;
  }

  public ParticipationBean toBean(ParticipationEntity participationEntity) {
    UserBean userBean = userRepository
      .findById(participationEntity.getUserId())
      .map(userAssemblyService::toBean)
      .orElseThrow(IllegalStateException::new);

    CourseBean courseBean = courseAssemblyService.toBean(participationEntity.getCourse());

    String created = timeService
      .dateToString(participationEntity.getCreated(), true)
      .orElseThrow(IllegalStateException::new);

    ParticipationBean participationBean = new ParticipationBean();
    participationBean.setId(participationEntity.getId());
    participationBean.setUser(userBean);
    participationBean.setCourse(courseBean);
    participationBean.setCreated(created);

    return participationBean;
  }

}
