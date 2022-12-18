import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { AuthSelectors } from '../auth/auth.selector';
import { postsAction } from './posts.action';
import { ExternalApiSelectors } from '../external-api/external-api.selector';
import { serviceMap } from '../../operator/service-map.operator';
import { asyncMap, pagedAsyncMap } from '../../operator/async-map.operator';
import { catchError, EMPTY, map, switchMap, tap } from 'rxjs';
import { subPostsAction } from '../sub-posts/sub-posts.action';

@Injectable()
export class PostsEffects {
	public fetchPagesCount = createEffect(() =>
		this.actions$.pipe(
			ofType(postsAction.fetchPagesCount),
			serviceMap(() => this.externalApiSelectors.postService()),
			asyncMap(
				(service, { authContext }) => service.pagesCount(20, authContext),
				this.authSelectors
			),
			map(pagesCount =>
				postsAction.fetchPagesCountSuccess({
					paginationBean: { pagesCount, pageSize: 20 },
				})
			),
			catchError(e => {
				console.error(e);
				return EMPTY;
			})
		)
	);

	public fatchPagesCountSuccess = createEffect(() =>
		this.actions$.pipe(
			ofType(postsAction.fetchPagesCountSuccess),
			map(payload => payload.paginationBean),
			serviceMap(() => this.externalApiSelectors.postService()),
			pagedAsyncMap(
				(service, { context, authContext, index }) =>
					service.fetchPage(index, context.pageSize, authContext),
				this.authSelectors
			),
			switchMap(posts => [
				postsAction.fetchPageSuccess({ posts }),
				subPostsAction.fetchPageSuccess({
					subPosts: posts
						.map(post => post.subPosts)
						.reduce((acc, nextSubPosts) => [...acc, ...nextSubPosts], []),
				}),
			])
		)
	);

	public constructor(
		private actions$: Actions,
		private externalApiSelectors: ExternalApiSelectors,
		private authSelectors: AuthSelectors
	) {}
}
