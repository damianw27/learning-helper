package pl.wilenskid.content.rest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wilenskid.commons.annotation.RestService;
import pl.wilenskid.content.db.ContentEntity;
import pl.wilenskid.content.service.ContentService;

import javax.inject.Inject;
import java.io.IOException;

@RestService
@RequestMapping("/content")
public class ContentRestService {

  private final ContentService contentService;

  @Inject
  public ContentRestService(ContentService contentService) {
    this.contentService = contentService;
  }

  @ResponseBody
  @GetMapping("/{filename}")
  public ResponseEntity<Resource> serveContent(@PathVariable("filename") String filename) throws IOException {
    Resource loadedResource = contentService.loadContentByName(filename);

    return ResponseEntity
      .ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadedResource.getFilename() + "\"")
      .body(loadedResource);
  }

  @ResponseBody
  @GetMapping("/html/{filename}")
  public ResponseEntity<String> serveHtmlFile(@PathVariable("filename") String filename) throws IOException {
    String content = contentService.loadContentAsString(filename);
    return ResponseEntity.ok(content);
  }

  @ResponseBody
  @PostMapping("/upload")
  public ResponseEntity<String> uploadFileAndGetUrl(@RequestParam("file") MultipartFile file) {
    ContentEntity contentEntity = contentService.uploadContent(file)
      .orElseThrow(IllegalStateException::new);

    return ResponseEntity.ok(contentEntity.getUrl());
  }

}
