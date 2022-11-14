package pl.wilenskid.api.assembly;

import pl.wilenskid.api.service.TimeService;
import pl.wilenskid.api.model.User;
import pl.wilenskid.api.model.bean.UserBean;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserAssembly {

  private final TimeService timeService;

  @Inject
  public UserAssembly(TimeService timeService) {
    this.timeService = timeService;
  }

  public UserBean toBean(User user) {
    String created = timeService
      .dateToString(user.getCreated(), false)
      .orElse(null);

    String updated = timeService
      .dateToString(user.getUpdated(), false)
      .orElse(null);

    UserBean userBean = new UserBean();
    userBean.setId(user.getId());
    userBean.setName(user.getName());
    userBean.setDisplayName(user.getDisplayName());
    userBean.setEmail(user.getEmail());
    userBean.setUserRole(user.getUserRole());
    userBean.setCreated(created);
    userBean.setUpdated(updated);
    return userBean;
  }

}
