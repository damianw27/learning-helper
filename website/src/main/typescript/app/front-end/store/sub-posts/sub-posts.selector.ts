import { Injectable } from '@angular/core';
import { FrontEndState } from '../front-end.state';
import { createSelector, MemoizedSelector, Store } from '@ngrx/store';
import { distinctUntilChanged } from 'rxjs';
import { SubPostBean } from '../../api/models/sub-post-bean';

@Injectable({ providedIn: 'root' })
export class SubPostsSelectors {
	private readonly selectSubPosts = createSelector(
		this.selectSubPostsState.bind(this),
		state =>
			Object.values(state.entities)
				.filter(subPost => subPost !== undefined)
				.map(subPost => subPost as SubPostBean)
	);

	public constructor(private readonly store: Store<FrontEndState>) {}

	public subPosts() {
		return this.store
			.select(state => this.selectSubPosts(state))
			.pipe(distinctUntilChanged());
	}

	private selectSubPostsState(state: FrontEndState) {
		return state.subPosts;
	}
}
