import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ExamInstance } from '../../model/exam-instance.model';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { ExamDefinition } from '../../model/exam-definition.model';

@Component({
  selector: 'app-exam-result',
  templateUrl: './exam-result.component.html',
  styleUrls: ['./exam-result.component.scss'],
})
export class ExamResultComponent implements OnInit {
  examInstance!: ExamInstance;
  examDefinition!: ExamDefinition;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private httpClient: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    firstValueFrom(this.activatedRoute.params)
      .then(params => this.loadExamInstance(params))
      .catch(() => this.router.navigateByUrl('/courses'))
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  loadExamInstance(params: Params): void {
    const examInstanceId = +params['id'];

    if (isNaN(examInstanceId)) {
      throw new Error('Invalid exam instance id!');
    }

    const request$ = this.httpClient.get<ExamInstance>(`/api/exam-instance/${examInstanceId}`);

    firstValueFrom(request$)
      .then(receivedExamInstance => (this.examInstance = receivedExamInstance))
      .then(receivedExamInstance => this.loadExamDefinition(receivedExamInstance))
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  loadExamDefinition(receivedExamInstance: ExamInstance): void {
    const request$ = this.httpClient.get<ExamDefinition>(`/api/exam-definition/${receivedExamInstance.examDefinitionId}`);

    firstValueFrom(request$)
      .then(receivedExamInstance => (this.examDefinition = receivedExamInstance))
      .finally(() => this.changeDetectorRef.detectChanges());
  }
}
