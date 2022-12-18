import { AsyncPipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';
import { Observable, Subscribable } from 'rxjs';

@Pipe({
	name: 'strictAsync',
	standalone: true,
})
export class StrictAsyncPipe extends AsyncPipe implements PipeTransform {
	public override transform<T>(
		obj: Observable<T> | Subscribable<T> | Promise<T> | null | undefined
	): T {
		if (obj === null || obj === undefined) {
			throw new Error('Passed null or undefined to strict async pipe!');
		}

		const effect = super.transform(obj);

		if (effect === null) {
			throw new Error('Invalid effect of pipe, returned null!');
		}

		return effect as T;
	}
}
