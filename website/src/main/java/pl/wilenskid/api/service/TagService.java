package pl.wilenskid.api.service;

import pl.wilenskid.api.model.Post;
import pl.wilenskid.api.service.repository.PostRepository;
import pl.wilenskid.api.model.Tag;
import pl.wilenskid.api.service.repository.TagRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
public class TagService {

  private final TagRepository tagRepository;
  private final PostRepository postRepository;

  @Inject
  public TagService(TagRepository tagRepository,
                    PostRepository postRepository) {
    this.tagRepository = tagRepository;
    this.postRepository = postRepository;
  }

  public void processTags(Collection<String> tags, Post post) {
    List<Tag> metaTags = tags
      .parallelStream()
      .map(String::toUpperCase)
      .map(tag -> processTag(tag, post))
      .collect(Collectors.toList());

    post.getTags().addAll(metaTags);
    postRepository.save(post);
  }

  private Tag processTag(String token, Post post) {
    return tagRepository
      .findByTokenUpper(token.toUpperCase())
      .map(tag -> updateTag(tag, post))
      .orElse(createTag(token, post));
  }

  private Tag createTag(String token, Post post) {
    Tag tag = new Tag();
    tag.setToken(token);
    tag.setTokenUpper(token.toUpperCase());
    tag.setPost(Set.of(post));
    return tagRepository.save(tag);
  }

  private Tag updateTag(Tag tag, Post post) {
    tag.getPost().add(post);
    return tagRepository.save(tag);
  }

}
