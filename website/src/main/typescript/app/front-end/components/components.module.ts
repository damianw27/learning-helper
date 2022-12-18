import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateTimePicker } from './date-time-picker/date-time-picker.component';
import { Pagination } from './pagination/pagination.component';
import { RichTextEditorComponent } from './rich-text-editor/rich-text-editor.component';
import { FormContainer } from './form-container/form-container.component';
import { QuillEditorComponent, QuillModule } from 'ngx-quill';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
	NgbDatepickerModule,
	NgbTimepickerModule,
} from '@ng-bootstrap/ng-bootstrap';
import { PostsListElementComponent } from './posts-list-element/posts-list-element.component';
import { RouterModule } from '@angular/router';
import { SubPostsComponent } from './sub-posts/sub-posts.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { SubPostCreateFormComponent } from './sub-post-create-form/sub-post-create-form.component';
import { SubPostElementComponent } from './sub-post-element/sub-post-element.component';

@NgModule({
	declarations: [
		DateTimePicker,
		Pagination,
		RichTextEditorComponent,
		FormContainer,
		PostsListElementComponent,
		RichTextEditorComponent,
		SubPostsComponent,
		SubPostCreateFormComponent,
		SubPostElementComponent,
	],
	exports: [
		DateTimePicker,
		Pagination,
		RichTextEditorComponent,
		FormContainer,
		PostsListElementComponent,
		RichTextEditorComponent,
		SubPostsComponent,
		SubPostCreateFormComponent,
		SubPostElementComponent,
	],
	imports: [
		CommonModule,
		ReactiveFormsModule,
		NgbTimepickerModule,
		NgbDatepickerModule,
		FormsModule,
		RouterModule,
		DragDropModule,
		QuillEditorComponent,
	],
})
export class ComponentsModule {}
