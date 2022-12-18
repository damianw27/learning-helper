import { UserCreateBean } from '../models/user-create-bean';
import { Observable } from 'rxjs';
import { UserBean } from '../models/user-bean';
import { UserUpdateBean } from '../models/user-update-bean';
import { UserAuthBean } from '../models/user-auth-bean';
import { AuthContext } from '../models/auth-context';
import { HttpClient } from '@angular/common/http';

export interface UserService {
	readonly login: (
		username: string,
		password: string
	) => Observable<UserAuthBean>;
	readonly logout: (authContext: AuthContext) => Observable<unknown>;
	readonly create: (
		userCreateBean: UserCreateBean,
		authContext: AuthContext
	) => Observable<UserBean>;
	readonly update: (
		userUpdateBean: UserUpdateBean,
		authContext: AuthContext
	) => Observable<UserBean>;
}
