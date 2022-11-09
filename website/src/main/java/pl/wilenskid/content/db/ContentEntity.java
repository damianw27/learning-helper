package pl.wilenskid.content.db;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import pl.wilenskid.user.db.UserEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "CONTENTS")
public class ContentEntity extends AbstractPersistable<Long> {

  private String name;

  private String type;

  private String url;

  private Long size;

  @ManyToOne
  private UserEntity owner;

}
