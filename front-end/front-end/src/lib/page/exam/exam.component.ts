import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CoursesApiService } from '../../service/courses-api.service';
import { AuthService } from '../../service/auth.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { ExamInstance } from '../../model/exam-instance.model';
import * as moment from 'moment';

@Component({
  selector: 'app-exam',
  templateUrl: './exam.component.html',
  styleUrls: ['./exam.component.scss'],
})
export class ExamComponent implements OnInit {
  examInstance!: ExamInstance;

  answersForm = this.formBuilder.group({});

  constructor(
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private courseApi: CoursesApiService,
    private router: Router,
    private changeDetectorRef: ChangeDetectorRef,
    private authService: AuthService,
    private coursesApiService: CoursesApiService,
    private modalService: NgbModal,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    firstValueFrom(this.activatedRoute.params)
      .then(params => this.loadExamInstance(params))
      .catch(() => this.router.navigateByUrl('/courses'));
  }

  loadExamInstance(params: Params): void {
    const examInstanceId = +params['id'];

    if (isNaN(examInstanceId)) {
      throw new Error('Invalid exam instance id!');
    }

    const request$ = this.httpClient.get<ExamInstance>(`/api/exam-instance/${examInstanceId}`);

    firstValueFrom(request$).then(receivedExamInstance => this.onGetExamInstanceSuccess(receivedExamInstance));
  }

  onGetExamInstanceSuccess(receivedExamInstance: ExamInstance): void {
    this.examInstance = receivedExamInstance;
    let formGroupFields: Record<string, [any, ((control: AbstractControl) => ValidationErrors | null)[]]> = {};
    this.examInstance.questions.map((question, index) => (formGroupFields[`answer${index}`] = ['', [Validators.required]]));
    this.answersForm = this.formBuilder.group(formGroupFields);
    this.changeDetectorRef.detectChanges();
  }

  public onSubmit(): void {
    const answers = Object.values(this.answersForm.value).reduce((previousValue, currentValue) => `${previousValue};${currentValue}`, '');

    const body = {
      id: this.examInstance.id,
      endDateTime: moment().format('YYYY-MM-DD hh:mm:ss'),
      answers,
    };

    const request$ = this.httpClient.post(`/api/exam-instance/update`, body);

    firstValueFrom(request$)
      .then(() => this.router.navigateByUrl(`/exam-result/${this.examInstance.id}`))
      .catch(console.error);
  }

  private getNumberAsTwoDigits(value: number) {
    return value.toLocaleString('en-US', { minimumIntegerDigits: 2, useGrouping: false });
  }
}
