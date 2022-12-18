import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-exams',
  templateUrl: './create-exam.component.html',
  styleUrls: ['./create-exam.component.scss'],
})
export class CreateExamComponent implements OnChanges, OnInit {
  @Input() courseId!: number;

  public examDefinitionForm = this.formBuilder.group({
    passLevel: [0.51, Validators.required],
    questionsCount: [5, Validators.required],
    attemptsCount: [1, Validators.required],
    startDateTime: ['', Validators.required],
    endDateTime: ['', Validators.required],
    courseId: [undefined, Validators.required],
  });

  constructor(
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.examDefinitionForm.setValue({
      ...this.examDefinitionForm.value,
      courseId: this.courseId,
    });
  }

  public ngOnChanges(changes: SimpleChanges): void {
    const changedCourseId = changes['courseId']?.currentValue;

    if (changedCourseId === undefined) {
      return;
    }


    this.examDefinitionForm.setValue({
      ...this.examDefinitionForm.value,
      courseId: changedCourseId,
    });
  }

  public onSubmit(event: SubmitEvent): void {
    if (this.examDefinitionForm.invalid) {
      event.preventDefault();
      return;
    }

    const request$ = this.httpClient.post(
      `/api/exam-definition/create`,
      this.examDefinitionForm.value
    );

    firstValueFrom(request$)
      .then(() => this.activeModal.close())
      .catch(console.error);

    event.preventDefault();
  }

  public isInvalid(): boolean {
    return this.examDefinitionForm.touched && this.examDefinitionForm.invalid;
  }

  get startDateTimeControl(): FormControl {
    return <FormControl>this.examDefinitionForm.controls['startDateTime'];
  }

  get endDateTimeControl(): FormControl {
    return <FormControl>this.examDefinitionForm.controls['endDateTime'];
  }
}
