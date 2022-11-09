package pl.wilenskid.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.commons.exception.ValidationErrorException;
import pl.wilenskid.commons.validation.ValidationErrors;
import pl.wilenskid.user.bean.UserAuthBean;
import pl.wilenskid.user.db.UserEntity;
import pl.wilenskid.user.db.UserRepository;
import pl.wilenskid.user.bean.UserBean;
import pl.wilenskid.user.bean.UserCreateBean;
import pl.wilenskid.user.enums.LearningStyle;
import pl.wilenskid.user.enums.UserRole;

import javax.inject.Inject;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.Calendar;

@RestService
@RequestMapping("/user")
public class UserRestService {

  private final UserRepository userRepository;
  private final UserValidatorService userRestValidator;
  private final UserAssemblyService userAssemblyService;
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  @Inject
  public UserRestService(UserRepository userRepository,
                         UserValidatorService userRestValidator,
                         UserAssemblyService userAssemblyService,
                         PasswordEncoder passwordEncoder,
                         UserService userService) {
    this.userRepository = userRepository;
    this.userRestValidator = userRestValidator;
    this.userAssemblyService = userAssemblyService;
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<UserBean> create(@RequestBody UserCreateBean userCreateBean) {
    ValidationErrors validationErrors = userRestValidator
      .validateCreateBean(userCreateBean);

    if (validationErrors.hasErrors()) {
      throw new ValidationErrorException(validationErrors);
    }

    UserEntity user = new UserEntity();
    user.setName(userCreateBean.getName());
    user.setDisplayName(userCreateBean.getDisplayName());
    user.setEmail(userCreateBean.getEmail());
    user.setPassword(passwordEncoder.encode(userCreateBean.getPassword()));

    if (userRepository.count() == 0) {
      user.setUserRole(UserRole.ADMINISTRATOR);
    } else {
      user.setUserRole(UserRole.STUDENT);
    }

    user.setTimezone(ZonedDateTime.now().getZone().getId());
    user.setCreated(Calendar.getInstance());
    user.setUpdated(null);
    user.setSuspendExpiration(null);
    user.setLearningStyle(null);

    UserEntity createdUser = userRepository.save(user);
    UserBean createdUserBean = userAssemblyService.toBean(createdUser);
    return ResponseEntity.ok(createdUserBean);
  }

  @ResponseBody
  @GetMapping("/login")
  public ResponseEntity<UserAuthBean> loginUser(Principal principal) {
    UserBean user = userRepository
      .getByName(principal.getName())
      .map(userAssemblyService::toBean)
      .orElseThrow(() -> new UsernameNotFoundException(principal.getName()));

    UserAuthBean userAuthBean = new UserAuthBean();
    userAuthBean.setAuthenticated(true);
    userAuthBean.setUser(user);

    return ResponseEntity.ok(userAuthBean);
  }

  @ResponseBody
  @PostMapping("/questionnaire/{answers}")
  public ResponseEntity<UserBean> analyzeQuestionnaireAndSetLearningStyle(@PathVariable("answers") String answers) {
    int aAnswersCount = 0;
    int bAnswersCount = 0;
    int cAnswersCount = 0;

    for (char answer : answers.toCharArray()) {
      if (answer == '0') aAnswersCount++;
      if (answer == '1') bAnswersCount++;
      if (answer == '2') cAnswersCount++;
    }

    LearningStyle learningStyle = null;

    if (aAnswersCount > bAnswersCount && aAnswersCount > cAnswersCount) learningStyle = LearningStyle.VISUAL;
    if (bAnswersCount > aAnswersCount && bAnswersCount > cAnswersCount) learningStyle = LearningStyle.AUDITORY;
    if (cAnswersCount > aAnswersCount && cAnswersCount > bAnswersCount) learningStyle = LearningStyle.KINESTHETIC;

    UserEntity loggedInUser = userService.getLoggedInUser();

    if (loggedInUser == null) {
      throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
    }

    loggedInUser.setLearningStyle(learningStyle);
    UserEntity updatedUser = userRepository.save(loggedInUser);
    return ResponseEntity.ok(userAssemblyService.toBean(updatedUser));
  }

}
