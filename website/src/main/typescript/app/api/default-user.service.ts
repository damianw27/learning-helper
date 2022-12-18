import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { AuthContextService } from '../service/auth-context.service';
import { UserService } from '../front-end/api/services/user-service';
import { UserCreateBean } from '../front-end/api/models/user-create-bean';
import { AuthContext } from '../front-end/api/models/auth-context';
import { UserBean } from '../front-end/api/models/user-bean';
import { UserAuthBean } from '../front-end/api/models/user-auth-bean';
import { UserUpdateBean } from '../front-end/api/models/user-update-bean';

@Injectable({
	providedIn: 'root',
})
export class DefaultUserService implements UserService {
	public constructor(
		private httpClient: HttpClient,
		private authContextService: AuthContextService
	) {}

	public readonly create = (
		userCreateBean: UserCreateBean,
		authContext: AuthContext
	): Observable<UserBean> => {
		const headers = this.authContextService.getHeaders(authContext);
		return this.httpClient.post<UserBean>('/api/user/create', userCreateBean, {
			headers,
		});
	};

	public readonly login = (
		username: string,
		password: string
	): Observable<UserAuthBean> => {
		const token = Buffer.from(`${username}:${password}`, 'utf-8').toString(
			'base64'
		);

		const headers = new HttpHeaders({
			Authorization: `Basic ${token}`,
			'Content-Type': 'application/x-www-form-urlencoded',
			'X-Requested-With': 'XMLHttpRequest',
		});

		return this.httpClient
			.get<UserAuthBean>('/api/user/login', { headers, observe: 'response' })
			.pipe(
				map(response => {
					const xsrfToken = response.headers.get('XSRF-TOKEN');

					if (response.body === null) {
						throw new Error('Invalid login response!');
					}

					return {
						...response.body,
						authContext: {
							type: 'Basic',
							token,
							xsrfToken: xsrfToken ?? '',
						},
					};
				})
			);
	};

	public logout(authContext: AuthContext): Observable<unknown> {
		const headers = this.authContextService.getHeaders(authContext);
		return this.httpClient.post<unknown>('/api/logout', {}, { headers });
	}

	public update(
		userUpdateBean: UserUpdateBean,
		authContext: AuthContext
	): Observable<UserBean> {
		const headers = this.authContextService.getHeaders(authContext);
		return this.httpClient.post<UserBean>('/api/user/update', userUpdateBean, {
			headers,
		});
	}
}
