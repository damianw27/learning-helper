package pl.wilenskid.api.assembly;

import pl.wilenskid.api.model.Tag;
import pl.wilenskid.api.model.bean.TagBean;

import javax.inject.Named;

@Named
public class TagAssembly {

  public TagBean toBean(Tag tag) {
    TagBean tagBean = new TagBean();
    tagBean.setId(tag.getId());
    tagBean.setToken(tagBean.getToken());
    tagBean.setTokenUpper(tagBean.getTokenUpper());
    return tagBean;
  }

}
