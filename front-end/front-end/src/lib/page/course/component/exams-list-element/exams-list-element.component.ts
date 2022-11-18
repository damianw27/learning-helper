import { Component, Input } from '@angular/core';
import { ExamDefinition } from '../../../../model/exam-definition.model';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ExamInstance } from '../../../../model/exam-instance.model';
import { firstValueFrom } from 'rxjs';
import * as moment from 'moment';

@Component({
  selector: 'app-exams-list-element',
  templateUrl: './exams-list-element.component.html',
  styleUrls: ['./exams-list-element.component.scss'],
})
export class ExamsListElementComponent {
  @Input() examDefinition!: ExamDefinition;

  constructor(private httpClient: HttpClient, private router: Router) {}

  onJoinClick(examDefinition: ExamDefinition): void {
    const examStartDateTime = moment(examDefinition.startDateTime, 'YYYY-MM-DD hh:mm:ss');
    const currentDateTime = moment();

    console.log(examStartDateTime, currentDateTime);

    if (currentDateTime.isBefore(examStartDateTime)) {
      console.error('current date is before exam start date');
      return;
    }

    const body = {
      startDateTime: currentDateTime.format('YYYY-MM-DD hh:mm:ss'),
      examDefinition,
    };

    const request$ = this.httpClient.post<ExamInstance>(`/api/exam-instance/create`, body);

    firstValueFrom(request$)
      .then(examInstance => this.router.navigateByUrl(`/exam/${examInstance.id}`))
      .catch(console.error);
  }

  private getNumberAsTwoDigits(value: number) {
    return value.toLocaleString('en-US', { minimumIntegerDigits: 2, useGrouping: false });
  }
}
