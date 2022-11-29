package pl.wilenskid.api.service.validator;

import pl.wilenskid.api.model.User;
import pl.wilenskid.api.model.bean.UserCreateBean;
import pl.wilenskid.api.model.bean.UserUpdateBean;
import pl.wilenskid.api.service.repository.UserRepository;
import pl.wilenskid.common.generics.service.ValidatorService;
import pl.wilenskid.common.model.ValidationErrors;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;
import java.util.regex.Pattern;

@Named
public class UserValidatorService implements ValidatorService<UserCreateBean, UserUpdateBean> {

  private static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
  private static final String PASSWORD_REGEX_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{12,}$";
  private static final String PASSWORD_FIELD = "password";

  private final UserRepository userRepository;

  @Inject
  public UserValidatorService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public ValidationErrors validateCreateBean(UserCreateBean userCreateBean) {
    ValidationErrors validationErrors = new ValidationErrors();
    validateUserName(validationErrors, userCreateBean.getName());
    validateUserNameLength(validationErrors, userCreateBean.getName());
    validateEmail(validationErrors, userCreateBean.getEmail());
    validatePasswords(validationErrors, userCreateBean.getPassword(), userCreateBean.getRePassword());
    return validationErrors;
  }

  @Override
  public ValidationErrors validateUpdateBean(UserUpdateBean userUpdateBean) {
    ValidationErrors validationErrors = new ValidationErrors();
    validateEmail(validationErrors, userUpdateBean.getEmail());
    return validationErrors;
  }

  private void validateUserName(ValidationErrors validationErrors, String name) {
    Optional<User> user = userRepository.getByName(name);

    if (user.isEmpty()) {
      return;
    }

    validationErrors.put("name", "User with given name already exists!");
  }

  private void validateUserNameLength(ValidationErrors validationErrors, String name) {
    if (name.length() >= 4) {
      return;
    }

    validationErrors.put("name", "Given name is too short!");
  }

  private void validateEmail(ValidationErrors validationErrors, String email) {
    boolean isEmailValid = Pattern
      .compile(EMAIL_REGEX_PATTERN)
      .matcher(email)
      .matches();

    if (isEmailValid) {
      return;
    }

    validationErrors
      .put("email", "Email has wrong format");
  }

  private void validatePasswords(ValidationErrors validationErrors, String password, String rePassword) {
    boolean isPasswordValid = Pattern
      .compile(PASSWORD_REGEX_PATTERN)
      .matcher(password)
      .matches();

    if (!isPasswordValid) {
      validationErrors
        .put(PASSWORD_FIELD, "At least one upper case")
        .put(PASSWORD_FIELD, "At least one lower case")
        .put(PASSWORD_FIELD, "At least one digit")
        .put(PASSWORD_FIELD, "At least one special character")
        .put(PASSWORD_FIELD, "Minimum 12 characters");
    }

    if (!password.equals(rePassword)) {
      validationErrors
        .put("rePassword", "Passwords not match");
    }
  }

}
