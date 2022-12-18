import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { Participation } from '../../../../../model/participation.model';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-participants',
  templateUrl: './participants.component.html',
  styleUrls: ['./participants.component.scss'],
})
export class ParticipantsComponent implements OnInit {
  @Input() courseId!: number;
  participants: Participation[] = [];

  constructor(
    public activeModal: NgbActiveModal,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    const request$ = this.httpClient.get<Participation[]>(
      `/api/participation/${this.courseId}`
    );

    firstValueFrom(request$)
      .then(
        (receivedParticipants) => (this.participants = receivedParticipants)
      )
      .catch(() => this.activeModal.dismiss('No data to display!'));
  }
}
