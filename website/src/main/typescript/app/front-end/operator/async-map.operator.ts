import {
	combineLatestWith,
	concatMap,
	exhaustMap,
	map,
	mergeMap,
	Observable,
	of,
	switchMap,
	tap,
} from 'rxjs';
import { AuthSelectors } from '../store/auth/auth.selector';
import { AuthContext } from '../api/models/auth-context';
import { PaginationBean } from '../api/models/pagination-bean';

interface ExecutionContext<K> {
	readonly context: K;
	readonly authContext: AuthContext;
}

interface ExecutionContextWithIndex<K> extends ExecutionContext<K> {
	readonly index: number;
}

export const asyncMap =
	<T, K, O>(
		operation: (service: T, context: ExecutionContext<K>) => Observable<O>,
		authSelectors: AuthSelectors
	) =>
	(source$: Observable<{ context: K; service: T }>) =>
		source$.pipe(
			combineLatestWith(authSelectors.authContext()),
			map(([{ context, service }, authContext]) => ({
				context: {
					context,
					authContext,
				},
				service,
			})),
			exhaustMap(({ context, service }) => operation(service, context))
		);

export const pagedAsyncMap =
	<T, O>(
		operation: (
			service: T,
			context: ExecutionContextWithIndex<PaginationBean>
		) => Observable<O>,
		authSelectors: AuthSelectors
	) =>
	(source$: Observable<{ context: PaginationBean; service: T }>) =>
		source$.pipe(
			combineLatestWith(authSelectors.authContext()),
			mergeMap(([{ context, service }, authContext]) =>
				of(...Array.from(Array(context.pagesCount).keys())).pipe(
					map(index => ({
						context: {
							context,
							authContext,
							index,
						},
						service,
					})),
					exhaustMap(({ context, service }) => operation(service, context))
				)
			)
		);
