import { questionsAdapter } from './questions.adapter';
import { QuestionsState } from './questions.state';
import { createReducer, on } from '@ngrx/store';
import { questionsAction } from './questions.action';

const initialState: QuestionsState = questionsAdapter.getInitialState({
  isLoaded: false,
});

export const questionsReducer = createReducer(
  initialState,
  on(questionsAction.fetchSuccess, (state, { questions }) =>
    questionsAdapter.addMany(questions, state)
  ),
  on(questionsAction.createSuccess, (state, { question }) =>
    questionsAdapter.addOne(question, state)
  ),
  on(questionsAction.updateSuccess, (state, { question }) =>
    questionsAdapter.updateOne({ id: question.id, changes: question }, state)
  ),
  on(questionsAction.deleteSuccess, (state, { questionId }) =>
    questionsAdapter.removeOne(questionId, state)
  )
);
