package pl.wilenskid.api.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.wilenskid.api.enums.UserRole;
import pl.wilenskid.api.model.User;
import pl.wilenskid.api.service.repository.UserRepository;

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

  public User getLoggedInUser() {
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

  public boolean getLoggedUserRole(UserRole userRole) {
    return getLoggedInUser().getUserRole() == userRole;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
      .getByName(username)
      .orElseThrow(IllegalStateException::new);
  }

}
