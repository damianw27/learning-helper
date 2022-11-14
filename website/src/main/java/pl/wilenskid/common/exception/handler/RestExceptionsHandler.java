package pl.wilenskid.common.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import pl.wilenskid.common.exception.ValidationErrorException;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionsHandler {

  @ExceptionHandler(ValidationErrorException.class)
  public ResponseEntity<Map<String, List<String>>> restExceptions(ValidationErrorException exception, WebRequest request) {
    return ResponseEntity
      .badRequest()
      .body(exception.getValidationErrors().getErrors());
  }

}
