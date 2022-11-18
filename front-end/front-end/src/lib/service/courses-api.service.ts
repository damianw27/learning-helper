import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Course } from '../model/course.model';
import { filter, mergeMap, Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CoursesApiService {
  constructor(private httpClient: HttpClient) {}

  public getCourses(): Observable<Course[]> {
    return this.httpClient.get<number>('/api/course/all/pages-count/20').pipe(
      filter(pagesCount => pagesCount !== 0),
      mergeMap(pagesCount => of([...Array(pagesCount).keys()])),
      mergeMap(pageIndex => this.httpClient.get<Course[]>(`/api/course/all?index=${pageIndex}&size=20`))
    );
  }

  public getCourse(courseId: number): Observable<Course> {
    return this.httpClient.get<Course>(`/api/course/${courseId}`);
  }

  public createCourse(courseCreateBean: Record<string, string>): Observable<Course> {
    return this.httpClient.post<Course>('/api/course/create', courseCreateBean);
  }

  public updateCourse(courseUpdateBean: Record<string, string>): Observable<Course> {
    return this.httpClient.post<Course>('/api/course/update', courseUpdateBean);
  }
}
