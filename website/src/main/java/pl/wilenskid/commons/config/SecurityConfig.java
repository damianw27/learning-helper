package pl.wilenskid.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.wilenskid.user.service.UserService;

import javax.inject.Inject;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] unauthenticatedUrls = {
      "/login",
      "/register",
      "/api/user/login",
      "/api/user/create"
    };

    private static final String[] authenticatedUrls = {
      "/courses",
      "/my-courses",
      "/course",
      "/lesson",
      "/exam-start",
      "/exam",
      "/exam-result",
      "/api"
    };

    private final UserService userService;

    @Inject
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void authorizationConfiguration(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .httpBasic()
        .and()
        .cors()
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authorizeHttpRequests()
        .antMatchers(unauthenticatedUrls).permitAll()
        .antMatchers(authenticatedUrls).authenticated()
        .anyRequest().permitAll()
        .and()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/api/login/perform")
        .and()
        .logout()
        .logoutUrl("/api/user/logout")
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
