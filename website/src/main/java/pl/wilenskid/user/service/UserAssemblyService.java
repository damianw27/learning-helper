package pl.wilenskid.user.service;

import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.user.db.UserEntity;
import pl.wilenskid.user.bean.UserBean;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserAssemblyService {

  private final TimeService timeService;

  @Inject
  public UserAssemblyService(TimeService timeService) {
    this.timeService = timeService;
  }

  public UserBean toBean(UserEntity user) {
    UserBean userBean = new UserBean();
    userBean.setId(user.getId());
    userBean.setName(user.getName());
    userBean.setDisplayName(user.getDisplayName());
    userBean.setEmail(user.getEmail());
    userBean.setUserRole(user.getUserRole());
    userBean.setCityName(user.getCityName());
    userBean.setCountry(user.getCountry());
    userBean.setTimezone(user.getTimezone());
    userBean.setCreated(timeService.dateToString(user.getCreated(), false).orElse(null));
    userBean.setUpdated(timeService.dateToString(user.getUpdated(), false).orElse(null));
    userBean.setLearningStyle(user.getLearningStyle());
    return userBean;
  }

}
