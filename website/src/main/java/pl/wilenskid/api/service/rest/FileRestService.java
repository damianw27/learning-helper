package pl.wilenskid.api.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

@Slf4j
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
  @GetMapping("/{filename}")
  public ResponseEntity<UploadedFileBean> getFileInfo(@PathVariable("filename") String filename) {
    return uploadedFileRepository
      .findByName(filename)
      .map(uploadedFileAssembly::toBean)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @GetMapping("/download/{filename}")
  public ResponseEntity<Resource> serveContent(@PathVariable("filename") String filename) {
    return uploadedFileRepository
      .findByName(filename)
      .map(this::buildDownloadResponse)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @GetMapping("/content/{filename}")
  public ResponseEntity<Resource> serveHtmlFile(@PathVariable("filename") String filename) {
    return uploadedFileRepository
      .findByName(filename)
      .map(UploadedFile::getName)
      .map(filesService::download)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @PutMapping("/upload")
  public ResponseEntity<UploadedFileBean> upload(@RequestParam("file") MultipartFile file) {
    if (file == null || file.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    UploadedFile uploadedFile = filesService.upload(file);
    return ResponseEntity.ok(uploadedFileAssembly.toBean(uploadedFile));
  }

  public ResponseEntity<Resource> buildDownloadResponse(UploadedFile uploadedFile) {
    Resource fileToDownload = filesService.download(uploadedFile.getName());
    long contentLength = 0;

    try {
      contentLength = fileToDownload.contentLength();
    } catch (IOException e) {
      log.error("Unable to read file content length.");
      return ResponseEntity.internalServerError().build();
    }

    String contentDispositionValue = String
      .format("attachment; filename=\"%s\"", uploadedFile.getOriginalName());

    return ResponseEntity
      .ok()
      .contentLength(contentLength)
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionValue)
      .body(fileToDownload);
  }

}
