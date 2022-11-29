package pl.wilenskid.api.assembly;

import pl.wilenskid.api.model.Question;
import pl.wilenskid.api.model.QuestionAnswer;
import pl.wilenskid.api.model.bean.QuestionAnswerBean;
import pl.wilenskid.api.model.bean.QuestionAnswerCreateBean;
import pl.wilenskid.api.model.bean.QuestionAnswerUpdateBean;
import pl.wilenskid.api.model.bean.QuestionBean;
import pl.wilenskid.api.service.FilesService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class QuestionAssembly {

  private final FilesService filesService;

  @Inject
  public QuestionAssembly(FilesService filesService) {
    this.filesService = filesService;
  }

  public List<QuestionBean> toBeans(List<Question> questions) {
    return questions
      .stream()
      .map(this::toBean)
      .collect(Collectors.toList());
  }

  public QuestionBean toBean(Question question) {
    List<QuestionAnswerBean> questionAnswers = question
      .getAnswers()
      .stream()
      .map(this::toBean)
      .collect(Collectors.toList());

    QuestionBean questionBean = new QuestionBean();
    questionBean.setId(question.getId());
    questionBean.setContent(filesService.download(question.getContent().getName()));
    questionBean.setAnswers(questionAnswers);
    return questionBean;
  }

  public QuestionAnswerBean toBean(QuestionAnswer questionAnswer) {
    QuestionAnswerBean questionAnswerBean = new QuestionAnswerBean();
    questionAnswerBean.setId(questionAnswerBean.getId());
    questionAnswerBean.setContent(filesService.download(questionAnswer.getContent().getName()));
    questionAnswerBean.setIsValid(questionAnswer.getIsValid());
    return questionAnswerBean;
  }

  public QuestionAnswerCreateBean toCreateBean(QuestionAnswerUpdateBean questionAnswerUpdateBean) {
    QuestionAnswerCreateBean questionAnswerCreateBean = new QuestionAnswerCreateBean();
    questionAnswerCreateBean.setContent(questionAnswerUpdateBean.getContent());
    questionAnswerCreateBean.setIsValid(questionAnswerUpdateBean.getIsValid());
    return questionAnswerCreateBean;
  }

}
