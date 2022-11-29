package pl.wilenskid.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.wilenskid.api.service.UserService;

import javax.inject.Inject;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] authenticatedUrls = {
    "/courses",
    "/my-courses",
    "/course",
    "/lesson",
    "/exam-start",
    "/exam",
    "/exam-result",
    "/api/**/*"
  };

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Inject
  public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Inject
  public void authorizationConfiguration(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userService)
      .passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers(authenticatedUrls).authenticated()
      .anyRequest().permitAll()
      .and().formLogin().loginPage("/login").loginProcessingUrl("/api/login/perform")
      .and().logout().logoutUrl("/api/user/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID")
      .and().httpBasic()
      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and().cors();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
