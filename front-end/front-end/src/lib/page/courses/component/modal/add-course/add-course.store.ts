import { Injectable } from '@angular/core';
import { ComponentStore } from '@ngrx/component-store';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { forkJoin, mergeMap, Observable, of, tap } from 'rxjs';
import { CoursesApiService } from '../../../../../service/courses-api.service';
import { Router } from '@angular/router';

@Injectable()
export class AddCourseStore extends ComponentStore<{}> {
  constructor(private coursesApiService: CoursesApiService, private router: Router) {
    super({});
  }

  readonly createCourse = this.effect((elements$: Observable<[NgbActiveModal, any]>) =>
    elements$.pipe(
      mergeMap(([modal, createBean]) => forkJoin([of(modal), this.coursesApiService.createCourse(createBean)])),
      tap(([modal]) => modal.close()),
      tap(([, course]) => this.router.navigateByUrl(`/course/${course.id}`))
    )
  );
}
