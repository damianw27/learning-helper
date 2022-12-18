import { Injectable } from '@angular/core';
import { AuthContextService } from '../service/auth-context.service';
import { FileService } from '../front-end/api/services/file-service';
import { AuthContext } from '../front-end/api/models/auth-context';
import { Observable } from 'rxjs';
import { UploadedFileBean } from '../front-end/api/models/uploaded-file-bean';
import { HttpClient } from '@angular/common/http';

@Injectable({
	providedIn: 'root',
})
export class DefaultFileService implements FileService {
	public constructor(
		private httpClient: HttpClient,
		private authContextService: AuthContextService
	) {}

	public fetchFile(
		filename: string,
		authContext: AuthContext
	): Observable<unknown> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.get<File>(`/api/file/content/${filename}`, {
			headers,
		});
	}

	public fileInfo(
		filename: string,
		authContext: AuthContext
	): Observable<UploadedFileBean> {
		const headers = this.authContextService.getHeaders(authContext);

		return this.httpClient.get<UploadedFileBean>(`/api/file/${filename}`, {
			headers,
		});
	}

	public uploadFile(
		file: File,
		authContext: AuthContext
	): Observable<UploadedFileBean> {
		const headers = this.authContextService.getHeaders(authContext);
		const uploadData = new FormData();
		uploadData.append('file', file, file.name);

		return this.httpClient.put<UploadedFileBean>(
			`/api/file/upload`,
			uploadData,
			{
				headers,
			}
		);
	}
}
