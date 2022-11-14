package pl.wilenskid.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.wilenskid.common.model.ValidationErrors;

@Getter
public class ValidationErrorException extends RuntimeException {

  private final ValidationErrors validationErrors;

  public ValidationErrorException(ValidationErrors validationErrors) {
    super(HttpStatus.BAD_REQUEST.getReasonPhrase());
    this.validationErrors = validationErrors;
  }
}
