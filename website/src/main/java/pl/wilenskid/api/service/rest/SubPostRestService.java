package pl.wilenskid.api.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.wilenskid.api.assembly.SubPostAssembly;
import pl.wilenskid.api.model.Post;
import pl.wilenskid.api.model.SubPost;
import pl.wilenskid.api.model.UploadedFile;
import pl.wilenskid.api.model.bean.SubPostBean;
import pl.wilenskid.api.model.bean.SubPostCreateBean;
import pl.wilenskid.api.model.bean.SubPostUpdateBean;
import pl.wilenskid.api.service.FilesService;
import pl.wilenskid.api.service.repository.PostRepository;
import pl.wilenskid.api.service.repository.SubPostRepository;
import pl.wilenskid.common.annotation.RestService;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
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
  private final SubPostAssembly subPostAssembly;

  @Inject
  public SubPostRestService(FilesService filesService,
                            SubPostRepository subPostRepository,
                            PostRepository postRepository,
                            SubPostAssembly subPostAssembly) {
    this.filesService = filesService;
    this.subPostRepository = subPostRepository;
    this.postRepository = postRepository;
    this.subPostAssembly = subPostAssembly;
  }

  @ResponseBody
  @GetMapping("/all")
  public ResponseEntity<List<SubPostBean>> getAll(@RequestParam("index") int index,
                                                  @RequestParam("size") int size) {
    List<SubPostBean> subPosts = StreamSupport
      .stream(subPostRepository.findAll().spliterator(), true)
      .skip((long) index * size)
      .limit(size)
      .map(subPostAssembly::toBean)
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
  public ResponseEntity<SubPostBean> create(@RequestBody SubPostCreateBean subPostCreateBean) {
    Post post = postRepository
      .findById(subPostCreateBean.getPostId())
      .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Post with provided ID not found."));

    SubPost subPost = new SubPost();
    subPost.setTitle(subPostCreateBean.getTitle());
    subPost.setDescription(null);
    subPost.setContent(null);
    subPost.setPost(post);
    subPost.setCreated(Calendar.getInstance());
    SubPost createdSubPost = subPostRepository.save(subPost);

    post.getSubPosts().add(subPost);
    postRepository.save(post);

    return ResponseEntity.ok(subPostAssembly.toBean(createdSubPost));
  }

  @ResponseBody
  @PutMapping("/update")
  public ResponseEntity<SubPostBean> update(@RequestBody SubPostUpdateBean subPostUpdateBean) {
    return subPostRepository
      .findById(subPostUpdateBean.getSubPostId())
      .map(subPost -> updateSubPost(subPost, subPostUpdateBean))
      .map(subPostAssembly::toBean)
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

    subPost.setTitle(subPostUpdateBean.getTitle());
    subPost.setDescription(description);
    subPost.setContent(content);
    subPost.setUpdated(Calendar.getInstance());

    return subPostRepository.save(subPost);
  }

}
