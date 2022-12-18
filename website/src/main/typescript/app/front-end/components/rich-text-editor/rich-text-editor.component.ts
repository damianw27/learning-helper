import { Component, Input } from '@angular/core';
import Quill from 'quill';
import { ImageHandler, VideoHandler } from 'ngx-quill-upload';
import { defaultRichTextModules } from './rich-text-editor-modules';
import { catchError, firstValueFrom, map, Observable, of } from 'rxjs';
import { FormControl } from '@angular/forms';
import { ExternalApiSelectors } from '../../store/external-api/external-api.selector';
import { serviceMap } from '../../operator/service-map.operator';
import { asyncMap } from '../../operator/async-map.operator';
import { AuthSelectors } from '../../store/auth/auth.selector';
import { EditorChangeContent, EditorChangeSelection } from 'ngx-quill';

Quill.register('modules/imageHandler', ImageHandler);
Quill.register('modules/videoHandler', VideoHandler);

@Component({
	selector: 'app-rich-text-editor',
	templateUrl: './rich-text-editor.component.html',
	styleUrls: ['./rich-text-editor.component.scss'],
})
export class RichTextEditorComponent {
	@Input()
	public formControlName = '';

	public modules = {
		...defaultRichTextModules,
		imageHandler: {
			upload: (file: File) => this.uploadFile(file),
			accepts: ['png', 'jpg', 'jpeg', 'jfif'],
		},
		videoHandler: {
			upload: (file: File) => this.uploadFile(file),
			accepts: ['mpeg', 'avi'],
		},
	};

	public constructor(
		private externalApiSelectors: ExternalApiSelectors,
		private authSelectors: AuthSelectors
	) {}

	private uploadFile(file: File): Promise<string> {
		return firstValueFrom(this.handleFileUpload(file));
	}

	private handleFileUpload(file: File): Observable<string> {
		return of(file).pipe(
			serviceMap(() => this.externalApiSelectors.fileService()),
			asyncMap(
				(service, { context, authContext }) =>
					service.uploadFile(context, authContext),
				this.authSelectors
			),
			map(uploadedFile => uploadedFile.url),
			// map(uploadedFile => 'test')
			catchError(response => {
				console.error(response);
				return of(response);
			})
		);
	}
}
