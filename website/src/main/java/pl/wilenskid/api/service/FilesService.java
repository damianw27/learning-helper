package pl.wilenskid.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import pl.wilenskid.api.model.UploadedFile;
import pl.wilenskid.api.service.repository.UploadedFileRepository;
import pl.wilenskid.common.model.StringMultipartFile;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Named
@Slf4j
public class FilesService {

  private final UserService userService;
  private final Path uploadsDirectory;
  private final UploadedFileRepository uploadedFileRepository;

  public FilesService(UserService userService,
                      @Value("${application.uploads-dir}") String uploadsDirectoryPath,
                      UploadedFileRepository uploadedFileRepository) {
    this.userService = userService;
    this.uploadsDirectory = Paths.get(uploadsDirectoryPath);
    this.uploadedFileRepository = uploadedFileRepository;
    this.verifyUploadsEnvironment();
  }

  public UploadedFile upload(String filename, String content) {
    MultipartFile file = new StringMultipartFile(filename, content);
    return upload(file);
  }

  public UploadedFile upload(MultipartFile file) {
    UploadedFile uploadedFile = new UploadedFile();
    uploadedFile.setName(getRandomizedName());
    uploadedFile.setOriginalName(file.getOriginalFilename());
    uploadedFile.setType(file.getContentType());
    uploadedFile.setUrl(String.format("/api/file/%s", uploadedFile.getName()));
    uploadedFile.setSize(file.getSize());
    uploadedFile.setOwner(userService.getLoggedInUser());

    upload(file, uploadedFile.getName());

    return uploadedFileRepository.save(uploadedFile);
  }

  public void upload(MultipartFile file, String newFilename) {
    Path destinationFile = this.uploadsDirectory
      .resolve(Paths.get(newFilename))
      .normalize()
      .toAbsolutePath();

    try {
      InputStream inputStream = file.getInputStream();
      Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      inputStream.close();
    } catch (IOException exception) {
      log.error(
        "Unable to update file '{}' because: {}",
        file.getOriginalFilename(),
        exception.getMessage()
      );
    }
  }

  public UploadedFile replace(UploadedFile oldFile, String filename, String content) {
    try {
      delete(oldFile);
      MultipartFile description = new StringMultipartFile(filename, content);
      return upload(description);
    } catch (IOException exception) {
      log.error(
        "Unable to update file '{}' because: {}",
        oldFile.getOriginalName(),
        exception.getMessage()
      );

      return null;
    }
  }

  public Resource download(String filename) {
    Path path = uploadsDirectory.resolve(filename);

    try {
      Resource resource = new UrlResource(path.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        log.error("Application is unable to read file or file not exists: '{}'", filename);
      }
    } catch (MalformedURLException e) {
      log.error("Application was unable to parse following file path: '{}'", path);
    }

    return null;
  }

  public boolean delete(UploadedFile uploadedFile) throws IOException {
    Path destinationFile = this.uploadsDirectory
      .resolve(Paths.get(uploadedFile.getName()))
      .normalize()
      .toAbsolutePath();

    Files.delete(destinationFile);
    return Files.exists(destinationFile);
  }

  private void verifyUploadsEnvironment() {
    File uploadDirectory = uploadsDirectory.toFile();

    if (uploadDirectory.isDirectory() && uploadDirectory.exists()) {
      return;
    }

    try {
      Files.createDirectory(uploadsDirectory.toAbsolutePath());
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to create content " + uploadDirectory.getAbsolutePath());
    }
  }

  private String getRandomizedName() {
    UUID uuid = UUID.randomUUID();
    return String.format("FILE-%s", uuid);
  }

}
