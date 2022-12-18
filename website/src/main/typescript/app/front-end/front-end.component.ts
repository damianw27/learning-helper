import { Component, Input, OnInit } from '@angular/core';
import { defaultExternalApi, ExternalApi } from './api/external-api';
import { Store } from '@ngrx/store';
import { FrontEndState } from './store/front-end.state';
import { externalApiAction } from './store/external-api/external-api.action';
import { NavigationEnd, Router } from '@angular/router';
import { AuthSessionService } from './services/auth-session.service';
import { AuthSelectors } from './store/auth/auth.selector';
import { filter, tap } from 'rxjs';

@Component({
	selector: 'app-front-end[externalApi]',
	templateUrl: `./front-end.component.html`,
	styleUrls: ['./front-end.component.scss'],
})
export class FrontEndComponent implements OnInit {
	@Input()
	public externalApi: ExternalApi = defaultExternalApi;

	public loggedUser$ = this.authSelectors.user();

	public constructor(
		private store: Store<FrontEndState>,
		private router: Router,
		private authSessionService: AuthSessionService,
		private authSelectors: AuthSelectors
	) {}

	public ngOnInit(): void {
		this.router.events
			.pipe(
				filter(event => event instanceof NavigationEnd),
				tap(() => this.authSessionService.verifyIsUserLoggedIn())
			)
			.subscribe();

		this.store.dispatch(
			externalApiAction.loadContext({ externalApi: this.externalApi })
		);
	}
}
