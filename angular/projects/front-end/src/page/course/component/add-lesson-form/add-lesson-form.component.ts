import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  Output,
  SimpleChanges,
} from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Lesson } from '../../../../model/lesson.model';

@Component({
  selector: 'app-add-lesson-form',
  templateUrl: './add-lesson-form.component.html',
  styleUrls: ['./add-lesson-form.component.scss'],
})
export class AddLessonFormComponent {
  @Input() sectionId!: number;

  @Output() addedNewLesson = new EventEmitter<Lesson>();

  lessonForm = this.formBuilder.group({
    sectionId: [this.sectionId, Validators.required],
    title: ['', Validators.required],
  });

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  public ngOnChanges(changes: SimpleChanges): void {
    const sectionIdChanged = changes['sectionId']?.currentValue;

    if (sectionIdChanged === undefined) {
      return;
    }

    this.lessonForm.setValue({
      sectionId: sectionIdChanged,
      title: '',
    });

    this.changeDetectorRef.detectChanges();
  }

  public onFormSubmit(event: SubmitEvent): void {
    if (this.lessonForm.invalid) {
      event.preventDefault();
      return;
    }

    const request$ = this.httpClient.post<Lesson>(
      '/api/lesson/create',
      this.lessonForm.value
    );

    firstValueFrom(request$)
      .then((lesson) => this.addedNewLesson.emit(lesson))
      .then(() => this.resetFormValues());

    event.preventDefault();
  }

  public getTitleClassName(): string {
    return this.isTitleInvalid() ? 'form-control is-invalid' : 'form-control';
  }

  private isTitleInvalid(): boolean {
    return this.lessonForm.touched && this.lessonForm.invalid;
  }

  private resetFormValues(): void {
    this.lessonForm.reset();

    this.lessonForm.setValue({
      sectionId: this.sectionId,
      title: '',
    });

    this.changeDetectorRef.detectChanges();
  }
}
