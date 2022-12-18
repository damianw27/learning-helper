package pl.wilenskid.api.model.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.nio.ByteBuffer;

@Data
@JsonAutoDetect
public class FileBean {

  private UploadedFileBean uploadedFileBean;
  private byte[] content;

  public void setContent(ByteBuffer content) {
    this.content = content.array();
  }
}
