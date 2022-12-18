import { EntityState } from '@ngrx/entity';
import { QuestionBean } from '../../api/models/question-bean';

export interface QuestionsState extends EntityState<QuestionBean> {
  readonly isLoaded: boolean;
}
