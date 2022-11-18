import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { Question } from '../../../../../model/question.model';

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.scss'],
})
export class CreateQuestionComponent implements OnInit {
  @Input() courseId!: number;

  @Output() questionCreated = new EventEmitter<Question>();

  public questionForm = this.formBuilder.group({
    courseId: [0, Validators.required],
    content: ['', Validators.required],
    answers: this.formBuilder.array([]),
  });

  constructor(
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.questionForm.setValue({
      ...this.questionForm.value,
      courseId: this.courseId,
    });
  }

  onAddAnswer(): void {
    const answerGroup = this.formBuilder.group({
      content: ['', Validators.required],
      isValid: [false],
    });

    this.answersArray.push(answerGroup);
    this.changeDetectorRef.detectChanges();
  }

  getContentFromFormGroup(formGroup: FormGroup): FormControl {
    return <FormControl>formGroup.controls['content'];
  }

  onSubmit(event: SubmitEvent): void {
    if (this.questionForm.invalid) {
      event.preventDefault();
      return;
    }

    const request$ = this.httpClient.post<Question>(`/api/question/create`, this.questionForm.value);

    firstValueFrom(request$)
      .then(createdQuestion => this.questionCreated.emit(createdQuestion))
      .then(() => this.activeModal.close())
      .catch(console.error);

    event.preventDefault();
  }

  get contentControl(): FormControl {
    return <FormControl>this.questionForm.controls['content'];
  }

  get answersArray(): FormArray {
    return <FormArray>this.questionForm.controls['answers'];
  }

  get answersGroups(): FormGroup[] {
    return <FormGroup[]>this.answersArray.controls;
  }
}
