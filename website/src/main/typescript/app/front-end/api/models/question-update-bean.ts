import { QuestionAnswerUpdateBean } from './question-answer-update-bean';

export interface QuestionUpdateBean {
  readonly id: number;
  readonly content: string;
  readonly answers: QuestionAnswerUpdateBean[];
}
