package pl.wilenskid.api.assembly;

import lombok.extern.slf4j.Slf4j;
import pl.wilenskid.api.model.UploadedFile;
import pl.wilenskid.api.model.bean.UploadedFileBean;

import javax.inject.Inject;
import javax.inject.Named;

@Slf4j
@Named
public class UploadedFileAssembly {

  private final UserAssembly userAssembly;

  @Inject
  public UploadedFileAssembly(UserAssembly userAssembly) {
    this.userAssembly = userAssembly;
  }

  public UploadedFileBean toBean(UploadedFile uploadedFile) {
    UploadedFileBean uploadedFileBean = new UploadedFileBean();
    uploadedFileBean.setName(uploadedFile.getName());
    uploadedFileBean.setOriginalName(uploadedFile.getOriginalName());
    uploadedFileBean.setType(uploadedFile.getType());
    uploadedFileBean.setUrl(uploadedFile.getUrl());
    uploadedFileBean.setSize(uploadedFile.getSize());
    uploadedFileBean.setOwner(userAssembly.toBean(uploadedFile.getOwner()));
    return uploadedFileBean;
  }

}
