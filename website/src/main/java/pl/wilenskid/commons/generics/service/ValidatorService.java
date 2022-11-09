package pl.wilenskid.commons.generics.service;

import pl.wilenskid.commons.validation.ValidationErrors;

public interface ValidatorService<CreateBean, UpdateBean> {

  ValidationErrors validateCreateBean(CreateBean createBean);

  ValidationErrors validateUpdateBean(UpdateBean updateBean);

}
