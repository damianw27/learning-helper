import { createAction, props } from '@ngrx/store';
import { QuestionBean } from '../../api/models/question-bean';
import { QuestionCreateBean } from '../../api/models/question-create-bean';
import { QuestionUpdateBean } from '../../api/models/question-update-bean';

export enum QuestionsActionType {
  Fetch = '[questions] fetch questions for sub-post',
  FetchSuccess = '[question] fetch for sub-post success',
  Create = '[question] create question',
  CreateSuccess = '[question] create question success',
  Update = '[question] update question',
  UpdateSuccess = '[question] update question success',
  Delete = '[question] delete question',
  DeleteSuccess = '[question] delete question success',
}

interface FetchPayload {
  readonly subTaskId: number;
}

interface FetchSuccessPayload {
  readonly questions: QuestionBean[];
}

interface CreatePayload {
  readonly questionCreateBean: QuestionCreateBean;
}

interface CreateSuccessPayload {
  readonly question: QuestionBean;
}

interface UpdatePayload {
  readonly questionUpdateBean: QuestionUpdateBean;
}

interface UpdateSuccessPayload {
  readonly question: QuestionBean;
}

interface DeletePayload {
  readonly questionId: number;
}

interface DeletePayloadSuccess {
  readonly questionId: number;
}

export const questionsAction = {
  fetch: createAction(QuestionsActionType.Fetch, props<FetchPayload>()),
  fetchSuccess: createAction(QuestionsActionType.FetchSuccess, props<FetchSuccessPayload>()),
  create: createAction(QuestionsActionType.Create, props<CreatePayload>()),
  createSuccess: createAction(QuestionsActionType.CreateSuccess, props<CreateSuccessPayload>()),
  update: createAction(QuestionsActionType.Update, props<UpdatePayload>()),
  updateSuccess: createAction(QuestionsActionType.UpdateSuccess, props<UpdateSuccessPayload>()),
  delete: createAction(QuestionsActionType.Delete, props<DeletePayload>()),
  deleteSuccess: createAction(QuestionsActionType.DeleteSuccess, props<DeletePayloadSuccess>()),
};
