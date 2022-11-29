package pl.wilenskid.api.service;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

@Named
public class TimeService {

  private final SimpleDateFormat simpleDateFormat;
  private final SimpleDateFormat simpleDateTimeFormat;

  @Inject
  public TimeService() {
    this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    this.simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  }

  public Optional<Calendar> dateFromString(String dateString) {
    return dateTimeFromString(dateString, simpleDateFormat);
  }

  public Optional<Calendar> dateTimeFromString(String dateTimeString) {
    return dateTimeFromString(dateTimeString, simpleDateTimeFormat);
  }

  public Optional<Calendar> dateTimeFromString(String dateTimeString, SimpleDateFormat dateFormat) {
    Calendar convertedDate = Calendar.getInstance();

    try {
      convertedDate.setTime(dateFormat.parse(dateTimeString));
    } catch (ParseException e) {
      return Optional.empty();
    }

    return Optional.of(convertedDate);
  }

  public Optional<String> dateToString(Calendar calendar, boolean isDateTime) {
    SimpleDateFormat dateFormat = isDateTime ? simpleDateTimeFormat : simpleDateFormat;
    return dateToString(calendar, dateFormat);
  }

  public Optional<String> dateToString(Calendar calendar, SimpleDateFormat dateFormat) {
    return calendar == null
      ? Optional.empty()
      : Optional.of(dateFormat.format(calendar.getTime()));
  }

}
