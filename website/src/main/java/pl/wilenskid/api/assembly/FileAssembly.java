package pl.wilenskid.api.assembly;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import pl.wilenskid.api.model.UploadedFile;
import pl.wilenskid.api.model.bean.FileBean;
import pl.wilenskid.api.service.FilesService;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

@Named
@Slf4j
public class FileAssembly {

  private final FilesService filesService;
  private final UploadedFileAssembly uploadedFileAssembly;

  @Inject
  public FileAssembly(FilesService filesService,
                      UploadedFileAssembly uploadedFileAssembly) {
    this.filesService = filesService;
    this.uploadedFileAssembly = uploadedFileAssembly;
  }

  public FileBean toBean(UploadedFile uploadedFile) {
    if (uploadedFile == null) {
      return null;
    }

    Resource fileResource = filesService.download(uploadedFile.getName());

    try (ReadableByteChannel bytesChannel = fileResource.readableChannel()) {
      ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileResource.contentLength());
      bytesChannel.read(byteBuffer);
      FileBean fileBean = new FileBean();
      fileBean.setUploadedFileBean(uploadedFileAssembly.toBean(uploadedFile));
      fileBean.setContent(byteBuffer);
      return fileBean;
    } catch (Exception  exception) {
      log.error("Unable to load file from resource.");
      return null;
    }
  }

}
