import { createAction, props } from '@ngrx/store';
import { PostBean } from '../../api/models/post-bean';
import { PostCreateBean } from '../../api/models/post-create-bean';
import { PostUpdateBean } from '../../api/models/post-update-bean';
import { PaginationBean } from '../../api/models/pagination-bean';

export enum PostsActionType {
	Fetch = '[posts] fetch all',
	FetchPagesCount = '[posts] fetch all pages count',
	FetchSuccess = '[posts] fetch all success',
	Create = '[posts] create post',
	CreateSuccess = '[posts] create post success',
	Update = '[posts] update post',
	UpdateSuccess = '[posts] update post success',
	Delete = '[posts] delete post',
	DeleteSuccess = '[posts] delete post success',
}

interface FetchPagesCountPayload {
	readonly pageSize: number;
}

interface FetchPagesCountSuccessPayload {
	readonly paginationBean: PaginationBean;
}

interface FetchPageSuccessPayload {
	readonly posts: PostBean[];
}

interface CreatePayload {
	readonly postCreateBean: PostCreateBean;
}

interface CreateSuccessPayload {
	readonly post: PostBean;
}

interface UpdatePayload {
	readonly updateCreateBean: PostUpdateBean;
}

interface UpdateSuccessPayload {
	readonly post: PostBean;
}

interface DeletePayload {
	readonly postId: string;
}

interface DeleteSuccessPayload {
	readonly postId: string;
}

export const postsAction = {
	fetchPagesCount: createAction(
		PostsActionType.Fetch,
		props<FetchPagesCountPayload>()
	),
	fetchPagesCountSuccess: createAction(
		PostsActionType.FetchPagesCount,
		props<FetchPagesCountSuccessPayload>()
	),
	fetchPageSuccess: createAction(
		PostsActionType.FetchSuccess,
		props<FetchPageSuccessPayload>()
	),
	create: createAction(PostsActionType.Create, props<CreatePayload>()),
	createSuccess: createAction(
		PostsActionType.CreateSuccess,
		props<CreateSuccessPayload>()
	),
	update: createAction(PostsActionType.Update, props<UpdatePayload>()),
	updateSuccess: createAction(
		PostsActionType.UpdateSuccess,
		props<UpdateSuccessPayload>()
	),
	delete: createAction(PostsActionType.Delete, props<DeletePayload>()),
	deleteSuccess: createAction(
		PostsActionType.DeleteSuccess,
		props<DeleteSuccessPayload>()
	),
};
