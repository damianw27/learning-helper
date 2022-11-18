import { Component, Input } from '@angular/core';
import Quill from 'quill';
import { ImageHandler, VideoHandler } from 'ngx-quill-upload';
import { defaultRichTextModules } from './rich-text-editor-modules';
import { catchError, firstValueFrom, map, mergeMap, Observable, of, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { FormControl } from '@angular/forms';

Quill.register('modules/imageHandler', ImageHandler);
Quill.register('modules/videoHandler', VideoHandler);

const toolbarOptions = {
  container: [['bold', 'italic', 'underline', 'strike'], ['emoji']],
  handlers: { emoji: function () {} },
};

@Component({
  selector: 'app-rich-text-editor',
  templateUrl: './rich-text-editor.component.html',
  styleUrls: ['./rich-text-editor.component.scss'],
})
export class RichTextEditorComponent {
  @Input() formControl: FormControl = new FormControl('');

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

  constructor(private httpClient: HttpClient) {}

  private uploadFile(file: File): Promise<string> {
    const uploadedFile$ = of(file).pipe(
      map(currentFile => this.createFormDataFromFile(currentFile)),
      mergeMap(formData => this.handleFileUpload(formData)),
      tap(console.log)
    );

    return firstValueFrom(uploadedFile$);
  }

  private createFormDataFromFile(file: File): FormData {
    const uploadData = new FormData();
    uploadData.append('file', file, file.name);
    return uploadData;
  }

  private handleFileUpload(formData: FormData): Observable<string> {
    return this.httpClient.post('/api/content/upload', formData, { responseType: 'text' }).pipe(
      catchError(response => {
        console.error(response);
        return of(response);
      })
    );
  }
}
