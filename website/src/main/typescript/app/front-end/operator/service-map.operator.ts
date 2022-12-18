import { combineLatestWith, map, Observable } from 'rxjs';

export const serviceMap =
	<T, K>(externalApiMap: () => Observable<T>) =>
	(source$: Observable<K>) => {
		return source$.pipe(
			combineLatestWith(externalApiMap()),
			map(([context, service]) => ({ context, service }))
		);
	};
