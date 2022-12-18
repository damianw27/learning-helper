import { createAction, props } from '@ngrx/store';
import { UserBean } from '../../api/models/user-bean';
import { AuthContext } from '../../api/models/auth-context';

export enum AuthActionType {
	Login = '[auth] login with basic',
	LoginSuccess = '[auth] login success',
	LoginFailed = '[auth] login failed',
	Logout = '[auth] logout',
}

export interface LoginPayload {
	readonly username: string;
	readonly password: string;
}

export interface LoginSuccessPayload {
	readonly user: UserBean;
	readonly authContext: AuthContext;
}

export interface LoginFailedPayload {
	readonly errorMessage: string;
}

export const authAction = {
	login: createAction(AuthActionType.Login, props<LoginPayload>()),
	loginSuccess: createAction(
		AuthActionType.LoginSuccess,
		props<LoginSuccessPayload>()
	),
	loginFailed: createAction(
		AuthActionType.LoginFailed,
		props<LoginFailedPayload>()
	),
	logout: createAction(AuthActionType.Logout),
};
