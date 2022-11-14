package pl.wilenskid.common.model;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StringMultipartFile implements MultipartFile {

  private static final String DESTINATION_PATH = System.getProperty("java.io.tmpdir");

  private final String filename;
  private final String contentType;
  private final String content;
  private final File file;

  private FileOutputStream fileOutputStream;

  public StringMultipartFile(String filename, String content) {
    this.filename = filename;
    this.contentType = "application/text";
    this.content = content;
    this.file = new File(DESTINATION_PATH + filename);
  }

  @Override
  public void transferTo(@NonNull File destination) throws IOException, IllegalStateException {
    fileOutputStream = new FileOutputStream(destination);
    fileOutputStream.write(getBytes());
  }

  public void clearOutStreams() throws IOException {
    if (null != fileOutputStream) {
      fileOutputStream.flush();
      fileOutputStream.close();
      file.deleteOnExit();
    }
  }

  @Override
  @NonNull
  public String getName() {
    return this.filename;
  }

  @Override
  public String getOriginalFilename() {
    return getName();
  }

  @Override
  public String getContentType() {
    return this.contentType;
  }

  @Override
  public boolean isEmpty() {
    return content.isEmpty() || content.isBlank();
  }

  @Override
  public long getSize() {
    return 0;
  }

  @Override
  @NonNull
  public byte[] getBytes() throws IOException {
    return content.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  @NonNull
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(getBytes());
  }

}
