import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateTimePicker } from './date-time-picker/date-time-picker.component';
import { Pagination } from './pagination/pagination.component';
import { RichTextEditor } from './rich-text-editor/rich-text-editor.component';
import { FormContainer } from './form-container/form-container.component';
import { QuillModule } from 'ngx-quill';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  NgbDatepickerModule,
  NgbTimepickerModule,
} from '@ng-bootstrap/ng-bootstrap';
import { PostsListElementComponent } from './posts-list-element/posts-list-element.component';
import { RouterModule } from '@angular/router';
import { SubPosts } from './sub-posts/sub-posts.component';
import { DragDropModule } from '@angular/cdk/drag-drop';

@NgModule({
  declarations: [
    DateTimePicker,
    Pagination,
    RichTextEditor,
    FormContainer,
    PostsListElementComponent,
    RichTextEditor,
    SubPosts,
  ],
  exports: [
    DateTimePicker,
    Pagination,
    RichTextEditor,
    FormContainer,
    PostsListElementComponent,
    RichTextEditor,
    SubPosts,
  ],
  imports: [
    CommonModule,
    QuillModule,
    ReactiveFormsModule,
    NgbTimepickerModule,
    NgbDatepickerModule,
    FormsModule,
    RouterModule,
    DragDropModule,
  ],
})
export class ComponentsModule {}
