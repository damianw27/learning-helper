import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { subPostsAction } from './sub-posts.action';
import { serviceMap } from '../../operator/service-map.operator';
import { ExternalApiSelectors } from '../external-api/external-api.selector';
import { asyncMap } from '../../operator/async-map.operator';
import { map, tap } from 'rxjs';
import { AuthSelectors } from '../auth/auth.selector';

@Injectable()
export class SubPostsEffect {
	public subPostCreate = createEffect(() =>
		this.actions$.pipe(
			ofType(subPostsAction.create),
			map(action => action.subPostCreateBean),
			serviceMap(() => this.externalApiSelectors.subPostService()),
			asyncMap(
				(service, { context, authContext }) =>
					service.create(context, authContext),
				this.authSelectors
			),
			map(subPost => subPostsAction.createSuccess({ subPost }))
		)
	);

	public subPostDelete = createEffect(() =>
		this.actions$.pipe(
			ofType(subPostsAction.delete),
			map(action => action.subPostId),
			serviceMap(() => this.externalApiSelectors.subPostService()),
			asyncMap(
				(service, { context, authContext }) =>
					service.delete(context, authContext),
				this.authSelectors
			),
			map(subPostId => subPostsAction.deleteSuccess({ subPostId }))
		)
	);

	public constructor(
		private actions$: Actions,
		private externalApiSelectors: ExternalApiSelectors,
		private authSelectors: AuthSelectors
	) {}
}
