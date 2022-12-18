import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthContextService } from '../service/auth-context.service';
import { QuestionService } from '../front-end/api/services/question-service';
import { AuthContext } from '../front-end/api/models/auth-context';
import { Observable } from 'rxjs';
import { QuestionBean } from '../front-end/api/models/question-bean';
import { QuestionCreateBean } from '../front-end/api/models/question-create-bean';
import { QuestionUpdateBean } from '../front-end/api/models/question-update-bean';

@Injectable({
	providedIn: 'root',
})
export class DefaultQuestionService implements QuestionService {
	public constructor(
		private httpClient: HttpClient,
		private authContextService: AuthContextService
	) {}

	public fetch(
		subPageId: number,
		authContext: AuthContext
	): Observable<QuestionBean[]> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.get<QuestionBean[]>(`/api/question/${subPageId}`, {
			headers,
		});
	}

	public create(
		questionCreateBean: QuestionCreateBean,
		authContext: AuthContext
	): Observable<QuestionBean> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.post<QuestionBean>(
			`/api/question/create`,
			questionCreateBean,
			{
				headers,
			}
		);
	}

	public update(
		questionUpdateBean: QuestionUpdateBean,
		authContext: AuthContext
	): Observable<QuestionBean> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.post<QuestionBean>(
			`/api/question/update`,
			questionUpdateBean,
			{
				headers,
			}
		);
	}

	public delete(
		questionId: number,
		authContext: AuthContext
	): Observable<unknown> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.delete<unknown>(`/api/question/${questionId}`, {
			headers,
		});
	}
}
