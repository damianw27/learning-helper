package pl.wilenskid.commons.validation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@JsonAutoDetect
public class ValidationErrors {

  private final HashMap<String, List<String>> errors;

  public ValidationErrors() {
    this.errors = new HashMap<>();
  }

  public ValidationErrors put(String fieldName, String message) {
    List<String> messages = errors.get(fieldName);

    if (messages == null) {
      messages = new ArrayList<>();
    } else {
      errors.remove(fieldName);
    }

    messages.add(message);
    errors.put(fieldName, messages);
    return this;
  }

  public boolean hasErrors() {
    return errors.size() != 0;
  }
}
