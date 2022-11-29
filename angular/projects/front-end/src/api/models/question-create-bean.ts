import { QuestionAnswerCreateBean } from './question-answer-create-bean';

export interface QuestionCreateBean {
  readonly subPageId: number;
  readonly content: FormData;
  readonly answers: QuestionAnswerCreateBean[];
}
