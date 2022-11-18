import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { ExamDefinition } from '../../../../model/exam-definition.model';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-exams-list',
  templateUrl: './exams-list.component.html',
  styleUrls: ['./exams-list.component.scss'],
})
export class ExamsListComponent implements OnInit {
  @Input() courseId!: number;
  public examDefs: ExamDefinition[] = [];

  constructor(private httpClient: HttpClient, private changeDetectorRef: ChangeDetectorRef) {}

  ngOnInit(): void {
    const request$ = this.httpClient.get<ExamDefinition[]>(`/api/exam-definition/by-course/${this.courseId}`);

    firstValueFrom(request$)
      .then(receivedExamDefs => (this.examDefs = receivedExamDefs))
      .finally(() => this.changeDetectorRef.detectChanges());
  }
}
