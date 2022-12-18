import { createEntityAdapter } from '@ngrx/entity';
import { QuestionBean } from '../../api/models/question-bean';

export const questionsAdapter = createEntityAdapter<QuestionBean>({
  selectId: model => model.id,
});
