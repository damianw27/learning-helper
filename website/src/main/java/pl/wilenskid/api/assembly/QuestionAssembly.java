package pl.wilenskid.api.assembly;

import pl.wilenskid.api.model.Question;
import pl.wilenskid.api.model.QuestionAnswer;
import pl.wilenskid.api.model.bean.QuestionAnswerBean;
import pl.wilenskid.api.model.bean.QuestionAnswerCreateBean;
import pl.wilenskid.api.model.bean.QuestionAnswerUpdateBean;
import pl.wilenskid.api.model.bean.QuestionBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class QuestionAssembly {

  private final FileAssembly fileAssembly;

  @Inject
  public QuestionAssembly(FileAssembly fileAssembly) {
    this.fileAssembly = fileAssembly;
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
    questionBean.setSubPostId(question.getSubPost().getId());
    questionBean.setContent(fileAssembly.toBean(question.getContent()));
    questionBean.setAnswers(questionAnswers);
    return questionBean;
  }

  public QuestionAnswerBean toBean(QuestionAnswer questionAnswer) {
    QuestionAnswerBean questionAnswerBean = new QuestionAnswerBean();
    questionAnswerBean.setId(questionAnswerBean.getId());
    questionAnswerBean.setContent(fileAssembly.toBean(questionAnswer.getContent()));
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
