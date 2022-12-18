package pl.wilenskid.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wilenskid.api.enums.UserRole;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@Entity(name = "USERS")
public class User extends AbstractPersistable<Long> implements UserDetails {

  private String name;

  private String displayName;

  private String email;

  private String password;

  private UserRole userRole;

  private Calendar created;

  private Calendar updated;

  private Calendar suspendExpiration;

  @ManyToMany
  private Set<Post> posts;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
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
