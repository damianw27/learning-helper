import { Component, OnInit } from '@angular/core';
import { ExamInstance } from '../../model/exam-instance.model';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['./grades.component.scss'],
})
export class GradesComponent implements OnInit {
  public examInstances: ExamInstance[] = [];

  constructor(private httpClient: HttpClient) {}

  ngOnInit(): void {
    const request$ = this.httpClient.get<ExamInstance[]>(
      '/api/exam-instance/current-user'
    );

    firstValueFrom(request$).then(
      (receivedExamInstances) => (this.examInstances = receivedExamInstances)
    );
  }
}
