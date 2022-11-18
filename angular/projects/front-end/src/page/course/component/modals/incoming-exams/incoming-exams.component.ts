import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { ExamDefinition } from '../../../../../model/exam-definition.model';

@Component({
  selector: 'app-incoming-exams',
  templateUrl: './incoming-exams.component.html',
  styleUrls: ['./incoming-exams.component.scss'],
})
export class IncomingExamsComponent implements OnInit {
  @Input() courseId!: number;
  exams: ExamDefinition[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    const request$ = this.httpClient.get<ExamDefinition[]>(
      `/api/exam-definition/by-course/${this.courseId}`
    );

    firstValueFrom(request$)
      .then((receivedExams) => (this.exams = receivedExams))
      .catch(() => this.activeModal.dismiss('No data to display!'));
  }

  onDeleteExamClick(examId: number): void {
    const request$ = this.httpClient.delete(`/api/exam-definition/${examId}`);

    firstValueFrom(request$)
      .then(() => this.removeExamFromListAndRefresh(examId))
      .catch(console.error);
  }

  private removeExamFromListAndRefresh(examId: number): void {
    this.exams = [...this.exams].filter((exam) => exam.id !== examId);
  }
}
