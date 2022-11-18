import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { Course } from '../../../../model/course.model';
import { AuthService } from '../../../../services/auth.service';
import { HttpClient } from '@angular/common/http';
import { Participation } from '../../../../model/participation.model';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-courses-element',
  templateUrl: './courses-element.component.html',
  styleUrls: ['./courses-element.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CoursesElementComponent implements OnInit {
  @Input() course!: Course;
  public loggedUser = this.authService.getLoggedUser();
  public isParticipating: boolean = false;

  @Output() courseDelete = new EventEmitter<Course>();

  constructor(
    private authService: AuthService,
    private httpClient: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  public ngOnInit(): void {
    const request$ = this.httpClient.get<Participation>(
      `/api/participation/logged-user/${this.course.id}`
    );

    firstValueFrom(request$)
      .then((response) => this.onParticipationRequestSuccess(response))
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  public sendParticipationRequest() {
    const request$ = this.httpClient.post(
      `/api/participation/${this.course.id}`,
      {}
    );
    firstValueFrom(request$)
      .then((response) => {
        if (response === null) {
          return;
        }

        this.isParticipating = true;
      })
      .catch(() => {
        this.isParticipating = false;
      })
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  private onParticipationRequestSuccess(response: Participation) {
    if (response === null) {
      return;
    }

    this.isParticipating = true;
  }

  onCourseDelete() {
    const request$ = this.httpClient.delete(`/api/course/${this.course.id}`);
    firstValueFrom(request$).then(() => this.courseDelete.emit(this.course));
  }
}
