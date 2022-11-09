package pl.wilenskid.user.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wilenskid.user.enums.LearningStyle;
import pl.wilenskid.user.enums.UserRole;

import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Collection;

@Getter
@Setter
@Entity(name = "USERS")
public class UserEntity extends AbstractPersistable<Long> implements UserDetails {

  private String name;

  private String displayName;

  private String email;

  private String password;

  private UserRole userRole;

  private String cityName;

  private String country;

  private String timezone;

  private Calendar created;

  private Calendar updated;

  private Calendar suspendExpiration;

  private LearningStyle learningStyle;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return getName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return suspendExpiration == null || suspendExpiration.after(Calendar.getInstance());
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
