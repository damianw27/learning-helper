package pl.wilenskid.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "UPLOADED_FILES")
public class UploadedFile extends AbstractPersistable<Long> {

  private String name;

  private String originalName;

  private String type;

  private String url;

  private Long size;

  @ManyToOne
  private User owner;

}
