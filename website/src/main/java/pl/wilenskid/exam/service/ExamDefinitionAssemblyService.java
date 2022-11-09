package pl.wilenskid.exam.service;

import pl.wilenskid.exam.bean.ExamDefinitionBean;
import pl.wilenskid.commons.service.TimeService;
import pl.wilenskid.exam.db.ExamDefinitionEntity;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ExamDefinitionAssemblyService {

  private final TimeService timeService;

  @Inject
  public ExamDefinitionAssemblyService(TimeService timeService) {
    this.timeService = timeService;
  }

  public ExamDefinitionBean toBean(ExamDefinitionEntity examDefinitionEntity) {
    String startDateTime = timeService
      .dateToString(examDefinitionEntity.getStartDateTime(), true)
      .orElseThrow(IllegalStateException::new);

    String endDateTime = timeService
      .dateToString(examDefinitionEntity.getEndDateTime(), true)
      .orElseThrow(IllegalStateException::new);

    ExamDefinitionBean examDefinitionBean = new ExamDefinitionBean();
    examDefinitionBean.setId(examDefinitionEntity.getId());
    examDefinitionBean.setPassLevel(examDefinitionEntity.getPassLevel());
    examDefinitionBean.setQuestionsCount(examDefinitionEntity.getQuestionsCount());
    examDefinitionBean.setAttemptsCount(examDefinitionEntity.getAttemptsCount());
    examDefinitionBean.setStartDateTime(startDateTime);
    examDefinitionBean.setEndDateTime(endDateTime);

    return examDefinitionBean;
  }

}
