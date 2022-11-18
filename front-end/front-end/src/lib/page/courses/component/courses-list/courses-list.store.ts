import { ComponentStore } from '@ngrx/component-store';
import { Course } from '../../../../model/course.model';
import { switchMap, tap } from 'rxjs';
import { Injectable } from '@angular/core';
import { CoursesApiService } from '../../../../service/courses-api.service';

interface CoursesListState {
  courses: Course[];
  pagesCount: number;
  currentPage: number;
}

@Injectable()
export class CoursesListStore extends ComponentStore<CoursesListState> {
  constructor(private coursesApiService: CoursesApiService) {
    super({
      courses: [],
      pagesCount: 0,
      currentPage: 0,
    });
  }

  readonly loadCourses = this.effect(() =>
    this.coursesApiService.getCourses().pipe(
      tap(courses => this.setCourses(courses)),
      tap(courses => this.setPagesCount(Math.ceil(courses.length / 20)))
    )
  );

  readonly setCourses = this.updater((state, courses: Course[]) => ({ ...state, courses }));

  readonly setPagesCount = this.updater((state, pagesCount: number) => ({ ...state, pagesCount }));

  readonly setCurrentPage = this.updater((state, currentPage: number) => ({ ...state, currentPage }));

  readonly selectCourses = this.select(state => state.courses.slice(state.currentPage * 20, state.currentPage * 20 + 20));

  readonly selectPagesCount = this.select(state => state.pagesCount);

  readonly selectCurrentPage = this.select(state => state.currentPage);
}
