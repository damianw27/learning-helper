import { Injectable } from '@angular/core';
import { createSelector, Store } from '@ngrx/store';
import { FrontEndState } from '../front-end.state';
import { distinctUntilChanged, Observable } from 'rxjs';
import { UserBean } from '../../api/models/user-bean';
import { AuthContext } from '../../api/models/auth-context';

@Injectable({ providedIn: 'root' })
export class AuthSelectors {
	private readonly selectAuth = (state: FrontEndState) => state.auth;

	private readonly selectUser = createSelector(
		this.selectAuth,
		state => state.user
	);

	private readonly selectAuthContext = createSelector(
		this.selectAuth,
		state => state.authContext
	);

	private readonly selectErrorMessage = createSelector(
		this.selectAuth,
		state => state.errorMessage
	);

	public constructor(private readonly store: Store<FrontEndState>) {}

	public user(): Observable<UserBean> {
		return this.store
			.select(state => this.selectUser(state))
			.pipe(distinctUntilChanged());
	}

	public authContext(): Observable<AuthContext> {
		return this.store
			.select(state => this.selectAuthContext(state))
			.pipe(distinctUntilChanged());
	}

	public errorMessage(): Observable<string | undefined> {
		return this.store
			.select(state => this.selectErrorMessage(state))
			.pipe(distinctUntilChanged());
	}
}
