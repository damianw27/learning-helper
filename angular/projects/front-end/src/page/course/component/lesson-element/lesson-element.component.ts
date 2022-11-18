import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Lesson } from '../../../../model/lesson.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-lesson-element',
  templateUrl: './lesson-element.component.html',
  styleUrls: ['./lesson-element.component.scss'],
})
export class LessonElementComponent {
  @Input() lesson!: Lesson;
  @Input() isEditMode: boolean = false;

  @Output() deleteLessonClick = new EventEmitter<number>();

  constructor() {}

  onDeleteClick(lessonId: number) {
    this.deleteLessonClick.emit(lessonId);
  }
}
