package pl.wilenskid.api.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wilenskid.api.model.Post;
import pl.wilenskid.api.model.UploadedFile;
import pl.wilenskid.api.model.User;
import pl.wilenskid.api.model.bean.PostCreateBean;
import pl.wilenskid.api.model.bean.PostUpdateBean;
import pl.wilenskid.api.service.FilesService;
import pl.wilenskid.api.service.UserService;
import pl.wilenskid.api.service.repository.PostRepository;
import pl.wilenskid.common.annotation.RestService;
import pl.wilenskid.common.model.StringMultipartFile;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@RestService
@RequestMapping("/post")
public class PostRestService {

  public static final String POST_DESCRIPTION_FILENAME = "post-description.html";

  private final PostRepository postRepository;
  private final FilesService filesService;
  private final UserService userService;

  @Inject
  public PostRestService(PostRepository postRepository,
                         FilesService filesService,
                         UserService userService) {
    this.postRepository = postRepository;
    this.filesService = filesService;
    this.userService = userService;
  }

  @ResponseBody
  @GetMapping("/all/pages-count/{size}")
  public ResponseEntity<Integer> getAllPagesCount(@PathVariable("size") Integer size) {
    int allEntriesCount = (int) postRepository.findAll().spliterator().estimateSize();
    int pagesCount = (int) Math.ceil(allEntriesCount / (double) size);
    return ResponseEntity.ok(pagesCount);
  }

  @ResponseBody
  @GetMapping("/all")
  public ResponseEntity<List<Post>> getAll(@RequestParam("index") Integer index,
                                           @RequestParam("size") Integer size) {
    List<Post> posts = StreamSupport
      .stream(postRepository.findAll().spliterator(), true)
      .skip((long) index * size)
      .limit(size)
      .collect(Collectors.toList());

    return ResponseEntity.ok(posts);
  }

  @ResponseBody
  @GetMapping("/search")
  public ResponseEntity<List<Post>> search(@QueryParam("query") String query) {
    List<String> tokens = Stream.of(query.split(" "))
      .map(token -> token.toUpperCase(Locale.ROOT))
      .collect(Collectors.toList());

    Stream<Post> postsStream = StreamSupport
      .stream(postRepository.findAll().spliterator(), false);

    if (query.equals("")) {
      return ResponseEntity.ok(postsStream.collect(Collectors.toList()));
    }

    List<Post> foundPosts = postsStream
      .filter(post ->
        post
          .getTags()
          .stream()
          .anyMatch(tag -> tokens.contains(tag.getToken().toUpperCase(Locale.ROOT)))
      )
      .filter(post ->
        tokens
          .stream()
          .anyMatch(tag -> post.getTitle().toUpperCase(Locale.ROOT).contains(tag))
      )
      .collect(Collectors.toList());

    return ResponseEntity.ok(foundPosts);
  }

  @ResponseBody
  @GetMapping("/{postId}")
  public ResponseEntity<Post> getById(@PathVariable("postId") Long postId) {
    return postRepository
      .findById(postId)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<Post> create(@RequestBody PostCreateBean postCreateBean) {
    MultipartFile description = new StringMultipartFile(
      POST_DESCRIPTION_FILENAME,
      postCreateBean.getDescription()
    );

    UploadedFile descriptionUploadedFile = filesService.upload(description);
    User loggedInUser = userService.getLoggedInUser();

    Post post = new Post();
    post.setTitle(postCreateBean.getTitle());
    post.setDescription(descriptionUploadedFile);
    post.setContributors(Set.of(loggedInUser));
    post.setCreated(Calendar.getInstance());
    post.setSubPosts(new HashSet<>());
    post.setTags(new HashSet<>());
    Post createdPost = postRepository.save(post);

    return ResponseEntity.ok(createdPost);
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<Post> update(@RequestBody PostUpdateBean postUpdateBean) {
    return postRepository
      .findById(postUpdateBean.getId())
      .map(post -> updatePost(post, postUpdateBean))
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Object> delete(@PathVariable("postId") Long postId) {
    Optional<Post> post = postRepository.findById(postId);

    if (post.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    postRepository.delete(post.get());
    return ResponseEntity.ok().build();
  }

  private Post updatePost(Post post, PostUpdateBean postUpdateBean) {
    UploadedFile description = filesService.replace(
      post.getDescription(),
      POST_DESCRIPTION_FILENAME,
      postUpdateBean.getDescription()
    );

    User loggedInUser = userService.getLoggedInUser();
    post.setTitle(postUpdateBean.getTitle());
    post.setUpdated(Calendar.getInstance());
    post.getContributors().add(loggedInUser);
    post.setDescription(description);

    return postRepository.save(post);
  }

}
