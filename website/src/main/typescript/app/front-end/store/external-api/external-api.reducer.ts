import { createReducer, on } from '@ngrx/store';
import { ExternalApiState } from './external-api.state';
import { externalApiAction } from './external-api.action';
import { EMPTY, empty, of } from 'rxjs';
import { defaultExternalApi } from '../../api/external-api';

const initialState: ExternalApiState = {
	externalApi: defaultExternalApi,
};

export const externalApiReducer = createReducer(
	initialState,
	on(externalApiAction.loadContext, (state, { externalApi }) => ({
		...state,
		externalApi,
	}))
);
