package pl.wilenskid.metatag.service;

import pl.wilenskid.course.db.CourseEntity;
import pl.wilenskid.course.db.CourseRepository;
import pl.wilenskid.metatag.db.MetaTagEntity;
import pl.wilenskid.metatag.db.MetaTagRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class MetaTagService {
  private final MetaTagRepository metaTagRepository;
  private final CourseRepository courseRepository;

  @Inject
  public MetaTagService(MetaTagRepository metaTagRepository,
                        CourseRepository courseRepository) {
    this.metaTagRepository = metaTagRepository;
    this.courseRepository = courseRepository;
  }

  public void createMetaTags(Collection<String> tags, CourseEntity courseEntity) {
    List<MetaTagEntity> metaTags = tags
      .parallelStream()
      .map(String::toUpperCase)
      .map(tag -> getOrCreateMetaTag(tag, courseEntity))
      .collect(Collectors.toList());

    courseEntity.getTags().addAll(metaTags);
    courseRepository.save(courseEntity);
  }

  private MetaTagEntity getOrCreateMetaTag(String tag, CourseEntity courseEntity) {
    return metaTagRepository
      .findByToken(tag)
      .orElse(createMetaTag(tag, courseEntity));
  }

  private MetaTagEntity createMetaTag(String tag, CourseEntity courseEntity) {
    MetaTagEntity metaTagEntity = new MetaTagEntity();
    metaTagEntity.setToken(tag);
    metaTagEntity.setCourse(courseEntity);
    return metaTagRepository.save(metaTagEntity);
  }
}
