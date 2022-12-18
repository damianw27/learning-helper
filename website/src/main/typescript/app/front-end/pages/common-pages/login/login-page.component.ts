import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AuthSessionService } from '../../../services/auth-session.service';
import { Store } from '@ngrx/store';
import { FrontEndState } from '../../../store/front-end.state';
import { authAction } from '../../../store/auth/auth.action';

@Component({
	selector: 'app-login',
	templateUrl: './login-page.component.html',
	styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {
	public loginFromGroup = this.formBuilder.group({
		username: [],
		password: [],
		rememberLogin: [],
	});

	public constructor(
		private store: Store<FrontEndState>,
		private formBuilder: FormBuilder,
		private authSessionService: AuthSessionService
	) {}

	public ngOnInit(): void {
		this.authSessionService.redirectToPostsWhenLoggedIn();
	}

	public login(): void {
		const username = this.loginFromGroup.get('username')?.value ?? '';
		const password = this.loginFromGroup.get('password')?.value ?? '';
		this.store.dispatch(authAction.login({ username, password }));
	}
}
