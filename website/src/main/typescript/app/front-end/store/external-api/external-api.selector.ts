import { FrontEndState } from '../front-end.state';
import { createSelector, Store } from '@ngrx/store';
import { distinctUntilChanged, Observable, tap } from 'rxjs';
import { FileService } from '../../api/services/file-service';
import { PostService } from '../../api/services/post-service';
import { QuestionService } from '../../api/services/question-service';
import { SubPostService } from '../../api/services/sub-post-service';
import { UserService } from '../../api/services/user-service';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ExternalApiSelectors {
	private readonly selectExternalApiState = (state: FrontEndState) =>
		state.externalApi.externalApi;

	private readonly selectFileService = createSelector(
		this.selectExternalApiState,
		state => state?.fileService
	);

	private readonly selectPostService = createSelector(
		this.selectExternalApiState,
		state => state?.postService
	);

	private readonly selectQuestionService = createSelector(
		this.selectExternalApiState,
		state => state?.questionService
	);

	private readonly selectSubPostService = createSelector(
		this.selectExternalApiState,
		state => state?.subPostService
	);

	private readonly selectUserService = createSelector(
		this.selectExternalApiState,
		state => state?.userService
	);

	public constructor(private readonly store: Store<FrontEndState>) {}

	public fileService = (): Observable<FileService> =>
		this.store
			.select(state => this.selectFileService(state))
			.pipe(distinctUntilChanged());

	public postService = (): Observable<PostService> =>
		this.store
			.select(state => this.selectPostService(state))
			.pipe(distinctUntilChanged());

	public questionService = (): Observable<QuestionService> =>
		this.store
			.select(state => this.selectQuestionService(state))
			.pipe(distinctUntilChanged());

	public subPostService = (): Observable<SubPostService> =>
		this.store
			.select(state => this.selectSubPostService(state))
			.pipe(distinctUntilChanged());

	public userService = (): Observable<UserService> =>
		this.store
			.select(state => this.selectUserService(state))
			.pipe(distinctUntilChanged());
}
