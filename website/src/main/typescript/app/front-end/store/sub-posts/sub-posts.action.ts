import { createAction, props } from '@ngrx/store';
import { SubPostBean } from '../../api/models/sub-post-bean';
import { SubPostCreateBean } from '../../api/models/sub-post-create-bean';
import { SubPostUpdateBean } from '../../api/models/sub-post-update-bean';
import { PaginationBean } from '../../api/models/pagination-bean';

export enum SubPostsActionType {
	Fetch = '[sub-pages] fetch sub-pages for page',
	FetchPagesCount = '[sub-posts] fetch pages count',
	FetchPageSuccess = '[sub-posts] fetch page success',
	Create = '[sub-posts] fetch create',
	CreateSuccess = '[sub-posts] fetch create success',
	Update = '[sub-posts] fetch update',
	UpdateSuccess = '[sub-posts] fetch update success',
	Delete = '[sub-posts] fetch delete',
	DeleteSuccess = '[sub-posts] fetch delete success',
}

interface FetchPagesCountPayload {
	readonly postId: number;
}

interface FetchPagesCountSuccessPayload {
	readonly postId: number;
	readonly paginationBean: PaginationBean;
}

interface FetchPageSuccessPayload {
	readonly subPosts: SubPostBean[];
}

interface CreatePayload {
	readonly subPostCreateBean: SubPostCreateBean;
}

interface CreateSuccessPayload {
	readonly subPost: SubPostBean;
}

interface UpdatePayload {
	readonly subPostUpdateBean: SubPostUpdateBean;
}

interface UpdateSuccessPayload {
	readonly subPost: SubPostBean;
}

interface DeletePayload {
	readonly subPostId: number;
}

interface DeleteSuccessPayload {
	readonly subPostId: number;
}

export const subPostsAction = {
	fetchPagesCount: createAction(
		SubPostsActionType.Fetch,
		props<FetchPagesCountPayload>()
	),
	fetchPagesCountSuccess: createAction(
		SubPostsActionType.FetchPagesCount,
		props<FetchPagesCountSuccessPayload>()
	),
	fetchPageSuccess: createAction(
		SubPostsActionType.FetchPageSuccess,
		props<FetchPageSuccessPayload>()
	),
	create: createAction(SubPostsActionType.Create, props<CreatePayload>()),
	createSuccess: createAction(
		SubPostsActionType.CreateSuccess,
		props<CreateSuccessPayload>()
	),
	update: createAction(SubPostsActionType.Update, props<UpdatePayload>()),
	updateSuccess: createAction(
		SubPostsActionType.UpdateSuccess,
		props<UpdateSuccessPayload>()
	),
	delete: createAction(SubPostsActionType.Delete, props<DeletePayload>()),
	deleteSuccess: createAction(
		SubPostsActionType.DeleteSuccess,
		props<DeleteSuccessPayload>()
	),
};
