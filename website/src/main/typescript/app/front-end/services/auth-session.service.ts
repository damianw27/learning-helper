import { Injectable } from '@angular/core';
import { AuthSelectors } from '../store/auth/auth.selector';
import { Router } from '@angular/router';
import { filter, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthSessionService {
	public constructor(
		private authSelectors: AuthSelectors,
		private router: Router
	) {}

	public readonly verifyIsUserLoggedIn = (): void => {
		this.authSelectors
			.user()
			.pipe(
				filter(loggedInUser => loggedInUser.id === -1),
				filter(() => this.router.url !== '/login'),
				tap(() => this.router.navigateByUrl('/login'))
			)
			.subscribe();
	};

	public readonly redirectToPostsWhenLoggedIn = (): void => {
		this.authSelectors
			.user()
			.pipe(
				filter(loggedInUser => loggedInUser.id !== -1),
				tap(() => this.router.navigateByUrl('/posts'))
			)
			.subscribe();
	};
}
