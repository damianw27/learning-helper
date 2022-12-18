import { PostsState } from './posts.state';
import { createReducer, on } from '@ngrx/store';
import { postsAdapter } from './posts.adapter';
import { postsAction } from './posts.action';

const initialState: PostsState = postsAdapter.getInitialState({
	pagesCount: 0,
	loadedPagesCount: 0,
	pageSize: 0,
});

export const postsReducer = createReducer(
	initialState,
	on(postsAction.fetchPagesCountSuccess, (state, { paginationBean }) => ({
		...state,
		pagesCount: paginationBean.pagesCount,
		pageSize: paginationBean.pageSize,
	})),
	on(postsAction.fetchPageSuccess, (state, { posts }) => ({
		...postsAdapter.addMany(posts, state),
		loadedPagesCount: state.loadedPagesCount + 1,
	})),
	on(postsAction.createSuccess, (state, { post }) =>
		postsAdapter.addOne(post, state)
	),
	on(postsAction.updateSuccess, (state, { post }) =>
		postsAdapter.updateOne({ id: post.id, changes: post }, state)
	),
	on(postsAction.deleteSuccess, (state, { postId }) =>
		postsAdapter.removeOne(postId, state)
	)
);
