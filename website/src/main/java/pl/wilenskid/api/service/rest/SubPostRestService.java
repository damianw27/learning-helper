package pl.wilenskid.api.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import pl.wilenskid.api.model.Post;
import pl.wilenskid.api.model.SubPost;
import pl.wilenskid.api.model.UploadedFile;
import pl.wilenskid.api.model.bean.SubPostCreateBean;
import pl.wilenskid.api.model.bean.SubPostUpdateBean;
import pl.wilenskid.api.service.FilesService;
import pl.wilenskid.api.service.repository.PostRepository;
import pl.wilenskid.api.service.repository.SubPostRepository;
import pl.wilenskid.common.annotation.RestService;
import pl.wilenskid.common.model.StringMultipartFile;
import pl.wilenskid.api.model.User;
import pl.wilenskid.api.service.UserService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestService
@RequestMapping("/sub-post")
public class SubPostRestService {

  private static final String SUB_POST_DESCRIPTION_FILENAME = "sub-post-description.html";
  private static final String SUB_POST_CONTENT_FILENAME = "sub-post-content.html";

  private final FilesService filesService;
  private final SubPostRepository subPostRepository;
  private final PostRepository postRepository;
  private final UserService userService;

  @Inject
  public SubPostRestService(FilesService filesService,
                            SubPostRepository subPostRepository,
                            PostRepository postRepository,
                            UserService userService) {
    this.filesService = filesService;
    this.subPostRepository = subPostRepository;
    this.postRepository = postRepository;
    this.userService = userService;
  }

  @ResponseBody
  @GetMapping("/all")
  public ResponseEntity<List<SubPost>> getAll(@RequestParam("index") int index,
                                              @RequestParam("size") int size) {
    List<SubPost> subPosts = StreamSupport
      .stream(subPostRepository.findAll().spliterator(), true)
      .skip((long) index * size)
      .limit(size)
      .collect(Collectors.toList());

    return ResponseEntity.ok(subPosts);
  }

  @ResponseBody
  @GetMapping("/all/pages-count/{size}")
  public ResponseEntity<Integer> getAllPagesCount(@PathVariable int size) {
    long subPostsCount = subPostRepository.findAll().spliterator().estimateSize();
    int pagesCount = (int) Math.ceil(subPostsCount / (double) size);
    return ResponseEntity.ok(pagesCount);
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<SubPost> create(@RequestBody SubPostCreateBean subPostCreateBean) throws IOException {
    Post post = postRepository
      .findById(subPostCreateBean.getPostId())
      .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Post with provided ID not found."));

    MultipartFile descriptionFile = new StringMultipartFile(SUB_POST_DESCRIPTION_FILENAME, subPostCreateBean.getDescription());
    UploadedFile description = filesService.upload(descriptionFile);

    MultipartFile contentFile = new StringMultipartFile(SUB_POST_DESCRIPTION_FILENAME, subPostCreateBean.getContent());
    UploadedFile content = filesService.upload(contentFile);

    SubPost subPost = new SubPost();
    subPost.setTitle(subPostCreateBean.getTitle());
    subPost.setDescription(description);
    subPost.setContent(content);
    subPost.setPost(post);
    subPost.setContributors(Set.of(userService.getLoggedInUser()));
    subPost.setCreated(Calendar.getInstance());
    SubPost createdSubPost = subPostRepository.save(subPost);

    post.getSubPosts().add(subPost);
    postRepository.save(post);

    return ResponseEntity.ok(createdSubPost);
  }

  @ResponseBody
  @PutMapping("/update")
  public ResponseEntity<SubPost> update(@RequestBody SubPostUpdateBean subPostUpdateBean) {
    return subPostRepository
      .findById(subPostUpdateBean.getSubPostId())
      .map(subPost -> updateSubPost(subPost, subPostUpdateBean))
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @DeleteMapping("/{subPostId}")
  public ResponseEntity<Object> delete(@PathVariable long subPostId) {
    Optional<SubPost> subPost = subPostRepository.findById(subPostId);

    if (subPost.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    subPostRepository.delete(subPost.get());
    return ResponseEntity.ok().build();
  }

  private SubPost updateSubPost(SubPost subPost, SubPostUpdateBean subPostUpdateBean) {
    UploadedFile description = filesService.replace(
      subPost.getDescription(),
      SUB_POST_DESCRIPTION_FILENAME,
      subPostUpdateBean.getDescription()
    );

    UploadedFile content = filesService.replace(
      subPost.getContent(),
      SUB_POST_CONTENT_FILENAME,
      subPostUpdateBean.getContent()
    );

    User loggedInUser = userService.getLoggedInUser();
    subPost.setTitle(subPostUpdateBean.getTitle());
    subPost.setDescription(description);
    subPost.setContent(content);
    subPost.getContributors().add(loggedInUser);
    subPost.setUpdated(Calendar.getInstance());

    return subPostRepository.save(subPost);
  }

}
