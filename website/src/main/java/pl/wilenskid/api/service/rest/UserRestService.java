package pl.wilenskid.api.service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.wilenskid.api.assembly.UserAssembly;
import pl.wilenskid.api.enums.UserRole;
import pl.wilenskid.api.model.User;
import pl.wilenskid.api.model.bean.UserAuthBean;
import pl.wilenskid.api.model.bean.UserBean;
import pl.wilenskid.api.model.bean.UserCreateBean;
import pl.wilenskid.api.model.bean.UserUpdateBean;
import pl.wilenskid.api.service.repository.UserRepository;
import pl.wilenskid.api.service.validator.UserValidatorService;
import pl.wilenskid.common.annotation.RestService;
import pl.wilenskid.common.exception.ValidationErrorException;
import pl.wilenskid.common.model.ValidationErrors;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Calendar;

@RestService
@RequestMapping("/user")
public class UserRestService {

  private final UserRepository userRepository;
  private final UserValidatorService userRestValidator;
  private final UserAssembly userAssembly;
  private final PasswordEncoder passwordEncoder;

  @Inject
  public UserRestService(UserRepository userRepository,
                         UserValidatorService userRestValidator,
                         UserAssembly userAssembly,
                         PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userRestValidator = userRestValidator;
    this.userAssembly = userAssembly;
    this.passwordEncoder = passwordEncoder;
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<UserBean> create(@RequestBody UserCreateBean userCreateBean) {
    ValidationErrors validationErrors = userRestValidator
      .validateCreateBean(userCreateBean);

    if (validationErrors.hasErrors()) {
      throw new ValidationErrorException(validationErrors);
    }

    UserRole userRole = userRepository.count() == 0
      ? UserRole.ADMINISTRATOR
      : UserRole.NORMAL;

    User user = new User();
    user.setName(userCreateBean.getName());
    user.setDisplayName(userCreateBean.getDisplayName());
    user.setEmail(userCreateBean.getEmail());
    user.setPassword(passwordEncoder.encode(userCreateBean.getPassword()));
    user.setUserRole(userRole);
    user.setCreated(Calendar.getInstance());
    user.setUpdated(null);
    user.setSuspendExpiration(null);
    User createdUser = userRepository.save(user);
    return ResponseEntity.ok(userAssembly.toBean(createdUser));
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<UserBean> create(@RequestBody UserUpdateBean userUpdateBean) {
    return userRepository
      .findById(userUpdateBean.getId())
      .map(user -> updateUser(user, userUpdateBean))
      .map(userAssembly::toBean)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @GetMapping("/login")
  public ResponseEntity<UserAuthBean> loginUser(Principal principal) {
    UserBean user = userRepository
      .getByName(principal.getName())
      .map(userAssembly::toBean)
      .orElseThrow(() -> new UsernameNotFoundException(principal.getName()));

    UserAuthBean userAuthBean = new UserAuthBean();
    userAuthBean.setAuthenticated(true);
    userAuthBean.setUser(user);
    return ResponseEntity.ok(userAuthBean);
  }

  private User updateUser(User user, UserUpdateBean userUpdateBean) {
    ValidationErrors validationErrors = userRestValidator.validateUpdateBean(userUpdateBean);

    if (validationErrors.hasErrors()) {
      throw new ValidationErrorException(validationErrors);
    }

    user.setDisplayName(user.getDisplayName());
    user.setDisplayName(user.getDisplayName());
    user.setEmail(userUpdateBean.getEmail());
    user.setUserRole(userUpdateBean.getUserRole());
    return userRepository.save(user);
  }

}
