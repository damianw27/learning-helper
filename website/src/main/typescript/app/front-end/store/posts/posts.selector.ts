import { Injectable } from '@angular/core';
import { createSelector, Store } from '@ngrx/store';
import { FrontEndState } from '../front-end.state';
import { distinctUntilChanged } from 'rxjs';

@Injectable({
	providedIn: 'root',
})
export class PostsSelectors {
	private readonly selectPostsState = (state: FrontEndState) => state.posts;

	private readonly selectPosts = createSelector(this.selectPostsState, state =>
		Object.values(state.entities)
	);

	private readonly selectPagesCount = createSelector(
		this.selectPostsState,
		state => state.pagesCount
	);

	private readonly selectLoadedPagesCount = createSelector(
		this.selectPostsState,
		state => state.loadedPagesCount
	);

	public constructor(private readonly store: Store<FrontEndState>) {}

	public readonly posts = () =>
		this.store
			.select(state => this.selectPosts(state))
			.pipe(distinctUntilChanged());

	public readonly pagesCount = () =>
		this.store
			.select(state => this.selectPagesCount(state))
			.pipe(distinctUntilChanged());

	public readonly loadedPagesCount = () =>
		this.store
			.select(state => this.selectLoadedPagesCount(state))
			.pipe(distinctUntilChanged());
}
