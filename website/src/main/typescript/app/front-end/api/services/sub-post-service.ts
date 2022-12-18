import { Observable } from 'rxjs';
import { SubPostBean } from '../models/sub-post-bean';
import { SubPostCreateBean } from '../models/sub-post-create-bean';
import { SubPostUpdateBean } from '../models/sub-post-update-bean';
import { AuthContext } from '../models/auth-context';

export interface SubPostService {
	readonly pagesCount: (
		pageSize: number,
		authContext: AuthContext
	) => Observable<number>;
	readonly fetchPage: (
		index: number,
		pageSize: number,
		authContext: AuthContext
	) => Observable<SubPostBean[]>;
	readonly create: (
		subPostCreateBean: SubPostCreateBean,
		authContext: AuthContext
	) => Observable<SubPostBean>;
	readonly update: (
		subPostUpdateBean: SubPostUpdateBean,
		authContext: AuthContext
	) => Observable<SubPostBean>;
	readonly delete: (
		subPostId: number,
		authContext: AuthContext
	) => Observable<number>;
}
