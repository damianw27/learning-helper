package pl.wilenskid.api.service.rest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wilenskid.api.assembly.UploadedFileAssembly;
import pl.wilenskid.api.model.UploadedFile;
import pl.wilenskid.api.model.bean.UploadedFileBean;
import pl.wilenskid.api.service.FilesService;
import pl.wilenskid.api.service.repository.UploadedFileRepository;
import pl.wilenskid.common.annotation.RestService;

import javax.inject.Inject;
import java.io.IOException;

@RestService
@RequestMapping("/file")
public class FileRestService {

  private final FilesService filesService;
  private final UploadedFileAssembly uploadedFileAssembly;
  private final UploadedFileRepository uploadedFileRepository;

  @Inject
  public FileRestService(FilesService filesService,
                         UploadedFileAssembly uploadedFileAssembly,
                         UploadedFileRepository uploadedFileRepository) {
    this.filesService = filesService;
    this.uploadedFileAssembly = uploadedFileAssembly;
    this.uploadedFileRepository = uploadedFileRepository;
  }

  @ResponseBody
  @GetMapping("/download/{filename}")
  public ResponseEntity<Resource> serveContent(@PathVariable("filename") String filename) {
    return uploadedFileRepository
      .findByName(filename)
      .map(uploadedFileAssembly::toBean)
      .map(this::getDownloadResponseEntity)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @GetMapping("/{filename}")
  public ResponseEntity<UploadedFileBean> serveHtmlFile(@PathVariable("filename") String filename) {
    return uploadedFileRepository
      .findByName(filename)
      .map(uploadedFileAssembly::toBean)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @PutMapping("/upload")
  public ResponseEntity<UploadedFileBean> upload(@RequestParam("file") MultipartFile file) throws IOException {
    if (file == null || file.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    UploadedFile uploadedFile = filesService.upload(file);
    return ResponseEntity.ok(uploadedFileAssembly.toBean(uploadedFile));
  }

  private ResponseEntity<Resource> getDownloadResponseEntity(UploadedFileBean uploadedFileBean) {
    String headerValue = String.format("attachment; filename=\"%s\"", uploadedFileBean.getOriginalName());

    return ResponseEntity
      .ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
      .body(uploadedFileBean.getContent());
  }

}
