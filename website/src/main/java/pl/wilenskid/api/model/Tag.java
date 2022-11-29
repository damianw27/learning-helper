package pl.wilenskid.api.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Setter
@Getter
@Entity(name = "TAGS")
@EqualsAndHashCode(callSuper = true)
public class Tag extends AbstractPersistable<Long> {
  private String token;

  private String tokenUpper;

  @ManyToMany
  @JoinTable(
    name = "POSTS_TAGS",
    joinColumns = @JoinColumn(name = "tag_id"),
    inverseJoinColumns = @JoinColumn(name = "post_id")
  )
  private Set<Post> post;
}
