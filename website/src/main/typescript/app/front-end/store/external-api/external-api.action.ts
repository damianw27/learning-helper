import { createAction, props } from '@ngrx/store';
import { ExternalApi } from '../../api/external-api';

export enum ExternalApiActionType {
  LoadContext = '[external-api] load external api',
}

interface LoadContextPayload {
  readonly externalApi: ExternalApi;
}

export const externalApiAction = {
  loadContext: createAction(ExternalApiActionType.LoadContext, props<LoadContextPayload>()),
};
