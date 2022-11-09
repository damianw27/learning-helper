package pl.wilenskid.content.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import pl.wilenskid.commons.config.PropertiesService;
import pl.wilenskid.content.bean.ContentCreateBean;
import pl.wilenskid.content.db.ContentEntity;
import pl.wilenskid.content.db.ContentRepository;
import pl.wilenskid.user.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Named
public class ContentService {

  private final Path rootLocation;
  private final ContentRepository contentRepository;
  private final UserService userService;
  private final ContentNameService contentNameService;

  @Inject
  public ContentService(ContentRepository contentRepository,
                        UserService userService,
                        ContentNameService contentNameService,
                        PropertiesService propertiesService) {
    this.rootLocation = Paths.get(propertiesService.getLocation());
    this.contentRepository = contentRepository;
    this.userService = userService;
    this.contentNameService = contentNameService;
  }

  public Optional<ContentEntity> uploadContent(MultipartFile file) {
    verifyUploadDirectory();

    if (file == null || file.isEmpty()) {
      return Optional.empty();
    }

    String randomizedFilename = contentNameService.getRandomizedName();

    Path destinationFile = this.rootLocation
      .resolve(Paths.get(randomizedFilename))
      .normalize()
      .toAbsolutePath();

    if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
      throw new IllegalStateException("Cannot store content outside current directory.");
    }

    try (InputStream inputStream = file.getInputStream()) {
      Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }

    ContentCreateBean contentCreateBean = new ContentCreateBean();
    contentCreateBean.setFileName(randomizedFilename);
    contentCreateBean.setFileUrl("/api/content/" + randomizedFilename);
    contentCreateBean.setFileType(file.getContentType());
    contentCreateBean.setFileSize(file.getSize());

    return Optional.of(createContentAndReturn(contentCreateBean));
  }

  public Optional<ContentEntity> uploadContent(String content) {
    verifyUploadDirectory();

    String randomizedFilename = contentNameService.getRandomizedName();

    Path destinationFile = this.rootLocation
      .resolve(Paths.get(randomizedFilename))
      .normalize()
      .toAbsolutePath();

    try (FileWriter fileWriter = new FileWriter(destinationFile.toString())) {
      fileWriter.write(content);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      return Optional.empty();
    }

    ContentCreateBean contentCreateBean = new ContentCreateBean();
    contentCreateBean.setFileName(randomizedFilename);
    contentCreateBean.setFileUrl("/api/content/" + randomizedFilename);
    contentCreateBean.setFileType("type/text");
    contentCreateBean.setFileSize((long) content.getBytes(StandardCharsets.UTF_8).length);

    return Optional.of(createContentAndReturn(contentCreateBean));
  }

  public Optional<ContentEntity> getContentById(long fileId) {
    return contentRepository
      .findById(fileId);
  }

  public Resource loadContentById(Long contentId) {
    ContentEntity contentEntity = contentRepository
      .findById(contentId)
      .orElseThrow(EntityNotFoundException::new);

    return loadContentByName(contentEntity.getName());
  }

  public String loadContentAsStringById(Long contentId) {
    ContentEntity contentEntity = contentRepository
      .findById(contentId)
      .orElseThrow(EntityNotFoundException::new);

    return loadContentAsString(contentEntity.getName());
  }

  public Resource loadContentByName(String filename) {
    Path path = rootLocation.resolve(filename);

    try {
      Resource resource = new UrlResource(path.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        System.err.println("Can not read content " + filename);
      }
    } catch (MalformedURLException e) {
      System.err.println("Could not read content: " + filename);
    }

    return null;
  }

  public String loadContentAsString(String filename) {
    Path path = rootLocation.resolve(filename);

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
      return bufferedReader.lines().reduce("", (acc, next) -> acc + next);
    } catch (IOException e) {
      System.err.println("Could not read file: " + filename);
    }

    return "";
  }

  public Optional<ContentEntity> updateContent(long fileId, String fileContent) {
    ContentEntity file = getContentById(fileId).orElseThrow(IllegalArgumentException::new);

    Path destinationFile = this.rootLocation
      .resolve(Paths.get(file.getName()))
      .normalize()
      .toAbsolutePath();

    try (FileWriter fileWriter = new FileWriter(destinationFile.toString())) {
      fileWriter.write(fileContent);
    } catch (IOException e) {
      return Optional.empty();
    }

    return Optional.of(file);
  }

  private void verifyUploadDirectory() {
    File uploadDirectory = rootLocation.toFile();

    if ((uploadDirectory.exists() && uploadDirectory.isDirectory()) || !uploadDirectory.exists()) {
      return;
    }

    if (!uploadDirectory.delete()) {
      throw new IllegalStateException("Unable to delete content " + uploadDirectory.getAbsolutePath());
    }

    try {
      Files.createDirectory(rootLocation.toAbsolutePath());
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to create content " + uploadDirectory.getAbsolutePath());
    }
  }

  private ContentEntity createContentAndReturn(ContentCreateBean contentCreateBean) {
    ContentEntity contentEntity = new ContentEntity();
    contentEntity.setName(contentCreateBean.getFileName());
    contentEntity.setUrl(contentCreateBean.getFileUrl());
    contentEntity.setType(contentCreateBean.getFileType());
    contentEntity.setSize(contentCreateBean.getFileSize());
    contentEntity.setOwner(userService.getLoggedInUser());
    return contentRepository.save(contentEntity);
  }

}
