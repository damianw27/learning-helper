package pl.wilenskid.commons.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;
import pl.wilenskid.commons.annotation.RestService;

import javax.inject.Inject;
import java.io.IOException;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Inject
  public WebConfig() {
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
      .addResourceHandler("/*", "/**/*")
      .addResourceLocations("classpath:/static/")
//      .setCachePeriod(3000)
      .resourceChain(true)
      .addResolver(getAngularResolver());
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix("/api", HandlerTypePredicate.forAnnotation(RestService.class));
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
      .addMapping("/**")
      .allowedOrigins("http://localhost:8080");
  }

  private PathResourceResolver getAngularResolver() {
    return new PathResourceResolver() {
      @Override
      protected Resource getResource(String resourcePath, Resource location) throws IOException {
        Resource requestedResource = location.createRelative(resourcePath);
        return requestedResource.exists() && requestedResource.isReadable()
          ? requestedResource
          : new ClassPathResource("/static/index.html");
      }
    };
  }

}
