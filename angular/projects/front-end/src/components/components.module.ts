import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DateTimePicker } from './date-time-picker/date-time-picker.component';
import { Pagination } from './pagination/pagination.component';
import { RichTextEditor } from './rich-text-editor/rich-text-editor.component';

@NgModule({
  declarations: [DateTimePicker, Pagination, RichTextEditor],
  exports: [DateTimePicker, Pagination, RichTextEditor],
  imports: [CommonModule],
})
export class ComponentsModule {}
