import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { Question } from '../../../../model/question.model';
import { Answer } from '../../../../model/answer.model';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-edit-question',
  templateUrl: './edit-question.component.html',
  styleUrls: ['./edit-question.component.scss'],
})
export class EditQuestionComponent implements OnInit {
  @Input() question!: Question;

  public questionForm = this.formBuilder.group({
    id: [0, Validators.required],
    courseId: [0, Validators.required],
    content: ['', Validators.required],
    answers: this.formBuilder.array([]),
  });

  constructor(
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.questionForm.setValue({
      id: this.question.id,
      courseId: this.question.courseId,
      content: this.question.content.content,
      answers: [],
    });

    this.question.answers
      .map((answer) => this.answerToFormGroup(answer))
      .forEach((answerGroup) => this.answersArray.push(answerGroup));
    this.changeDetectorRef.detectChanges();
  }

  onAddAnswer(): void {
    const answerGroup = this.formBuilder.group({
      id: [-1],
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

    const request$ = this.httpClient.post<Question>(
      `/api/question/update`,
      this.questionForm.value
    );

    firstValueFrom(request$)
      .then((updatedQuestion) => (this.question = updatedQuestion))
      .catch(console.error)
      .finally(() => this.changeDetectorRef.detectChanges());

    event.preventDefault();
  }

  private answerToFormGroup(answer: Answer): FormGroup {
    return this.formBuilder.group({
      id: [answer.id],
      content: [answer.content.content, Validators.required],
      isValid: [answer.isValid],
    });
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
