import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { AuthContext } from '../front-end/api/models/auth-context';

@Injectable({
	providedIn: 'root',
})
export class AuthContextService {
	public readonly getHeaders = (authContext: AuthContext): HttpHeaders =>
		new HttpHeaders({
			Authentication: `${authContext.type} ${authContext.token}`,
			// 'Content-Type': 'application/x-www-form-urlencoded',
			'X-Requested-With': 'XMLHttpRequest',
			// ["X-XSRF-TOKEN"]: authContext.xsrfToken,
		});
}
