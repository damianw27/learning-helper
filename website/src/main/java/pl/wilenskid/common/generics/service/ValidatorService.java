package pl.wilenskid.common.generics.service;

import pl.wilenskid.common.model.ValidationErrors;

public interface ValidatorService<CreateBean, UpdateBean> {

  ValidationErrors validateCreateBean(CreateBean createBean);

  ValidationErrors validateUpdateBean(UpdateBean updateBean);

}
