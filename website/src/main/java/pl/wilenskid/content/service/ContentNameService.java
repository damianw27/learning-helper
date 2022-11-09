package pl.wilenskid.content.service;

import javax.inject.Named;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;

@Named
public class ContentNameService {

  private final SecureRandom secureRandom;

  public ContentNameService() {
    this.secureRandom = new SecureRandom();
  }

  public String getRandomizedName() {
    long timeInMillis = Calendar.getInstance().getTimeInMillis();
    return ("FILE" + (timeInMillis + getRandomIntString())).toUpperCase();
  }

  private String getRandomIntString() {
    Random random = new Random();
    secureRandom.setSeed(random.nextInt());
    int randomNumber = Math.abs(secureRandom.nextInt());
    return String.valueOf(randomNumber);
  }

}
