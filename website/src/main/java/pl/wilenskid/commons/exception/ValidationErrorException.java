package pl.wilenskid.commons.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import pl.wilenskid.commons.validation.ValidationErrors;

@Getter
public class ValidationErrorException extends RuntimeException {

  private final ValidationErrors validationErrors;

  public ValidationErrorException(ValidationErrors validationErrors) {
    super(HttpStatus.BAD_REQUEST.getReasonPhrase());
    this.validationErrors = validationErrors;
  }
}
