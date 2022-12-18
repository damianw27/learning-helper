import { Injectable } from '@angular/core';
import { PostService } from '../front-end/api/services/post-service';
import { HttpClient } from '@angular/common/http';
import { AuthContextService } from '../service/auth-context.service';
import { PostCreateBean } from '../front-end/api/models/post-create-bean';
import { AuthContext } from '../front-end/api/models/auth-context';
import { Observable } from 'rxjs';
import { PostBean } from '../front-end/api/models/post-bean';
import { PostUpdateBean } from '../front-end/api/models/post-update-bean';

@Injectable({
	providedIn: 'root',
})
export class DefaultPostService implements PostService {
	public constructor(
		private httpClient: HttpClient,
		private authContextService: AuthContextService
	) {}

	public pagesCount(
		chunkSize: number,
		authContext: AuthContext
	): Observable<number> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.get<number>(`/api/post/all/pages-count/${chunkSize}`, {
			headers,
		});
	}

	public fetchPage(
		index: number,
		chunkSize: number,
		authContext: AuthContext
	): Observable<PostBean[]> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.get<PostBean[]>(
			`/api/post/all?index=${index}&size=${chunkSize}`,
			{
				headers,
			}
		);
	}

	public search(
		query: string,
		authContext: AuthContext
	): Observable<PostBean[]> {
		const escapedQuery = encodeURIComponent(query);
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.get<PostBean[]>(
			`/api/post/search?query=${escapedQuery}`,
			{ headers }
		);
	}

	public fetchById(
		pageId: number,
		authContext: AuthContext
	): Observable<PostBean> {
		const headers = this.authContextService.getHeaders(authContext);
		return this.httpClient.get<PostBean>(`/api/post/${pageId}`, { headers });
	}

	public create(
		postCreateBean: PostCreateBean,
		authContext: AuthContext
	): Observable<PostBean> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.post<PostBean>(`/api/post/create`, postCreateBean, {
			headers,
		});
	}

	public update(
		postUpdateBean: PostUpdateBean,
		authContext: AuthContext
	): Observable<PostBean> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.post<PostBean>(`/api/post/update`, postUpdateBean, {
			headers,
		});
	}

	public delete(postId: number, authContext: AuthContext): Observable<unknown> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.delete<unknown>(`/api/post/${postId}`, {
			headers,
		});
	}
}
