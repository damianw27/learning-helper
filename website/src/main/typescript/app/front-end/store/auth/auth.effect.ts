import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { ExternalApiSelectors } from '../external-api/external-api.selector';
import { authAction } from './auth.action';
import { catchError, map, mergeMap, Observable, of, tap } from 'rxjs';
import { AuthSelectors } from './auth.selector';
import { Router } from '@angular/router';
import { UserAuthBean } from '../../api/models/user-auth-bean';
import { serviceMap } from '../../operator/service-map.operator';
import { asyncMap } from '../../operator/async-map.operator';
import { defaultAuthContext } from '../../api/models/auth-context';

@Injectable()
export class AuthEffects {
	public login$ = createEffect(() =>
		this.action$.pipe(
			ofType(authAction.login),
			serviceMap(() => this.externalApiSelectors.userService()),
			asyncMap(
				(service, { context }) =>
					service.login(context.username, context.password),
				this.authSelectors
			),
			map((userAuthBean: UserAuthBean) =>
				authAction.loginSuccess({
					user: userAuthBean.user,
					authContext: userAuthBean.authContext ?? defaultAuthContext,
				})
			),
			catchError(e => {
				console.error(e);
				return of(authAction.loginFailed({ errorMessage: '' }));
			}),
			tap(() => this.router.navigateByUrl('/posts'))
		)
	);

	public logout$ = createEffect(
		() =>
			this.action$.pipe(
				ofType(authAction.logout),
				serviceMap(() => this.externalApiSelectors.userService()),
				asyncMap(
					(service, { authContext }) => service.logout(authContext),
					this.authSelectors
				)
			),
		{ dispatch: false }
	);

	public constructor(
		private action$: Actions,
		private externalApiSelectors: ExternalApiSelectors,
		private authSelectors: AuthSelectors,
		private router: Router
	) {}

	private login(username: string, password: string): Observable<any> {
		return this.externalApiSelectors.userService().pipe(
			mergeMap(service =>
				service.login(username, password).pipe(
					map((userAuthBean: UserAuthBean) =>
						authAction.loginSuccess({
							user: userAuthBean.user,
							authContext: userAuthBean.authContext ?? {
								type: 'none',
								token: '',
								xsrfToken: '',
							},
						})
					),
					catchError(e => {
						console.error(e);
						return of(authAction.loginFailed({ errorMessage: '' }));
					}),
					tap(() => this.router.navigateByUrl('/posts'))
				)
			)
		);
	}
}
