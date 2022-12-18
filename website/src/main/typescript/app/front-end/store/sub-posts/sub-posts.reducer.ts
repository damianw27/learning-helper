import { SubPostsState } from './sub-posts.state';
import { subPostsAdapter } from './sub-posts.adapter';
import { createReducer, on } from '@ngrx/store';
import { subPostsAction } from './sub-posts.action';

const initialState: SubPostsState = subPostsAdapter.getInitialState({});

export const subPostsReducer = createReducer(
  initialState,
  on(subPostsAction.fetchPageSuccess, (state, { subPosts }) =>
    subPostsAdapter.addMany(subPosts, state)
  ),
  on(subPostsAction.createSuccess, (state, { subPost }) => subPostsAdapter.addOne(subPost, state)),
  on(subPostsAction.updateSuccess, (state, { subPost }) =>
    subPostsAdapter.updateOne({ id: subPost.id, changes: subPost }, state)
  ),
  on(subPostsAction.deleteSuccess, (state, { subPostId }) =>
    subPostsAdapter.removeOne(subPostId, state)
  )
);
