import { createReducer, on } from '@ngrx/store';
import { AuthState } from './auth.state';
import { authAction } from './auth.action';
import { UserRole } from '../../api/models/enums/user-role';

const initialState: AuthState = {
  user: {
    id: -1,
    name: 'anonymous',
    displayName: 'Anonymous',
    email: '',
    userRole: UserRole.Anonymous,
    created: '',
    updated: '',
  },
  authContext: {
    type: 'none',
    token: '',
    xsrfToken: '',
  },
};

export const authReducer = createReducer(
  initialState,
  on(authAction.loginSuccess, (state, { user, authContext }) => ({
    ...state,
    user,
    authContext,
    errorMessage: undefined,
  })),
  on(authAction.loginFailed, (state, { errorMessage }) => ({
    ...state,
    errorMessage,
  })),
  on(authAction.logout, state => ({
    ...state,
    authContext: {
      type: 'none',
      token: '',
      xsrfToken: '',
    },
  }))
);
