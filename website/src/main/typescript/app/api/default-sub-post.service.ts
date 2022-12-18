import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthContextService } from '../service/auth-context.service';
import { SubPostService } from '../front-end/api/services/sub-post-service';
import { AuthContext } from '../front-end/api/models/auth-context';
import { map, Observable } from 'rxjs';
import { SubPostBean } from '../front-end/api/models/sub-post-bean';
import { SubPostCreateBean } from '../front-end/api/models/sub-post-create-bean';
import { SubPostUpdateBean } from '../front-end/api/models/sub-post-update-bean';

@Injectable({
	providedIn: 'root',
})
export class DefaultSubPostService implements SubPostService {
	public constructor(
		private httpClient: HttpClient,
		private authContextService: AuthContextService
	) {}

	public pagesCount(
		chunkSize: number,
		authContext: AuthContext
	): Observable<number> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.get<number>(
			`/api/sub-post/all/pages-count/${chunkSize}`,
			{
				headers,
			}
		);
	}

	public fetchPage(
		index: number,
		chunkSize: number,
		authContext: AuthContext
	): Observable<SubPostBean[]> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.get<SubPostBean[]>(
			`/api/sub-post/all?index=${index}&size=${chunkSize}`,
			{
				headers,
			}
		);
	}

	public create(
		subPostCreateBean: SubPostCreateBean,
		authContext: AuthContext
	): Observable<SubPostBean> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.post<SubPostBean>(
			`/api/sub-post/create`,
			subPostCreateBean,
			{
				headers,
			}
		);
	}

	public update(
		subPostUpdateBean: SubPostUpdateBean,
		authContext: AuthContext
	): Observable<SubPostBean> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.post<SubPostBean>(
			`/api/sub-post/update`,
			subPostUpdateBean,
			{
				headers,
			}
		);
	}

	public delete(
		subPostId: number,
		authContext: AuthContext
	): Observable<number> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient
			.delete<unknown>(`/api/sub-post/${subPostId}`, {
				headers,
			})
			.pipe(map(() => subPostId));
	}
}
