package pl.wilenskid.user.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.wilenskid.user.db.UserEntity;
import pl.wilenskid.user.db.UserRepository;
import pl.wilenskid.user.enums.UserRole;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
@Transactional
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Inject
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserEntity getLoggedInUser() {
    Object principal = SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();

    String username = principal instanceof UserDetails
      ? ((UserDetails) principal).getUsername()
      : principal.toString();

    return userRepository
      .getByName(username)
      .orElse(null);
  }

  public boolean hasLoggedInUserRole(UserRole userRole) {
    UserEntity loggedInUser = getLoggedInUser();
    return loggedInUser.getUserRole() == userRole;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
      .getByName(username)
      .orElseThrow(IllegalStateException::new);
  }

}
