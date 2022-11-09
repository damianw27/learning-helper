package pl.wilenskid.commons.generics.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

public abstract class EntityDefinition<Bean> extends AbstractPersistable<Long> {

  public abstract Bean toBean();

}
