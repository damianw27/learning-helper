import { filter, Observable, OperatorFunction } from 'rxjs';

export const typedFilter =
	<T>(operation: (value: T | undefined | null) => boolean) =>
	(source$: Observable<T | undefined | null>) =>
		source$.pipe(
			filter(operation) as OperatorFunction<T | undefined | null, T>
		);
