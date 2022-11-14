package pl.wilenskid.api.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pl.wilenskid.api.assembly.QuestionAssembly;
import pl.wilenskid.api.model.Question;
import pl.wilenskid.api.model.QuestionAnswer;
import pl.wilenskid.api.model.SubPost;
import pl.wilenskid.api.model.UploadedFile;
import pl.wilenskid.api.model.bean.*;
import pl.wilenskid.api.service.FilesService;
import pl.wilenskid.api.service.repository.QuestionAnswerRepository;
import pl.wilenskid.api.service.repository.QuestionRepository;
import pl.wilenskid.api.service.repository.SubPostRepository;
import pl.wilenskid.common.annotation.RestService;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RestService
@RequestMapping("/question")
public class QuestionRestService {

  private final SubPostRepository subPostRepository;
  private final QuestionRepository questionRepository;
  private final QuestionAnswerRepository questionAnswerRepository;
  private final FilesService filesService;
  private final QuestionAssembly questionAssembly;

  @Inject
  public QuestionRestService(SubPostRepository subPostRepository,
                             QuestionRepository questionRepository,
                             QuestionAnswerRepository questionAnswerRepository,
                             FilesService filesService,
                             QuestionAssembly questionAssembly) {
    this.subPostRepository = subPostRepository;
    this.questionRepository = questionRepository;
    this.questionAnswerRepository = questionAnswerRepository;
    this.filesService = filesService;
    this.questionAssembly = questionAssembly;
  }

  @ResponseBody
  @GetMapping("/{subPageId}")
  public ResponseEntity<List<QuestionBean>> getAllForSubPage(@PathVariable("subPageId") long subPageId) {
    return subPostRepository.findById(subPageId)
      .map(questionRepository::findAllBySubPost)
      .map(questionAssembly::toBeans)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }

  @ResponseBody
  @PostMapping("/create")
  public ResponseEntity<QuestionBean> create(@RequestBody QuestionCreateBean questionCreateBean) {
    Question question = createQuestion(questionCreateBean);

    questionCreateBean
      .getAnswers()
      .stream()
      .map(questionAnswer -> createQuestionAnswer(questionAnswer, question))
      .forEach(questionAnswer -> question.getAnswers().add(questionAnswer));

    questionRepository.save(question);

    return ResponseEntity.ok(questionAssembly.toBean(question));
  }

  @ResponseBody
  @PostMapping("/update")
  public ResponseEntity<QuestionBean> update(@RequestBody QuestionUpdateBean questionUpdateBean) {
    Question updatedQuestion = questionRepository
      .findById(questionUpdateBean.getId())
      .map(question -> updateQuestion(question, questionUpdateBean))
      .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

    updatedQuestion
      .getAnswers()
      .forEach(
        questionAnswer ->
          updateQuestionAnswer(
            questionAnswer,
            questionUpdateBean
              .getQuestionAnswerUpdateBean(questionAnswer)
              .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND))
          )
      );

    questionUpdateBean
      .getAnswers()
      .stream()
      .filter(updateBean -> updateBean.getId() == -1)
      .map(questionAssembly::toCreateBean)
      .map(createBean -> createQuestionAnswer(createBean, updatedQuestion))
      .forEach(questionAnswer -> updatedQuestion.getAnswers().add(questionAnswer));

    Question question = questionRepository.save(updatedQuestion);

    return ResponseEntity.ok(questionAssembly.toBean(question));
  }

  private Question createQuestion(QuestionCreateBean questionCreateBean) {
    SubPost subPost = subPostRepository
      .findById(questionCreateBean.getSubPageId())
      .orElseThrow(EntityNotFoundException::new);

    UploadedFile content = filesService.upload(
      "question_content.html",
      questionCreateBean.getContent()
    );

    Question question = new Question();
    question.setSubPost(subPost);
    question.setContent(content);
    question.setAnswers(new HashSet<>());
    return questionRepository.save(question);
  }

  private Question updateQuestion(Question question, QuestionUpdateBean questionUpdateBean) {
    UploadedFile content = filesService.replace(
      question.getContent(),
      "question_content.html",
      questionUpdateBean.getContent()
    );

    question.setContent(content);
    return questionRepository.save(question);
  }

  private QuestionAnswer createQuestionAnswer(QuestionAnswerCreateBean questionAnswerCreateBean,
                                              Question question) {
    UploadedFile answerContent = filesService.upload(
      "answer_content.html",
      questionAnswerCreateBean.getContent()
    );

    QuestionAnswer questionAnswer = new QuestionAnswer();
    questionAnswer.setQuestion(question);
    questionAnswer.setContent(answerContent);
    questionAnswer.setIsValid(questionAnswerCreateBean.getIsValid());
    return questionAnswerRepository.save(questionAnswer);
  }

  private void updateQuestionAnswer(QuestionAnswer questionAnswer,
                                              QuestionAnswerUpdateBean questionAnswerUpdateBean) {
    UploadedFile content = filesService.replace(
      questionAnswer.getContent(),
      "question_answer_content.html",
      questionAnswerUpdateBean.getContent()
    );

    questionAnswer.setContent(content);
    questionAnswer.setIsValid(questionAnswerUpdateBean.getIsValid());
    questionAnswerRepository.save(questionAnswer);
  }

}
