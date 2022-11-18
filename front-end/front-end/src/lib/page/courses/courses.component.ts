import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddCourseComponent } from './component/modal/add-course/add-course.component';
import { FormBuilder } from '@angular/forms';
import { Course } from '../../model/course.model';
import { HttpClient } from '@angular/common/http';
import { filter, firstValueFrom, mergeMap, of } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit {
  public searchForm = this.formBuilder.group({
    query: [''],
  });

  public courses: Course[] = [];
  public pagesCount: number = 1;
  public pageIndex: number = 0;

  constructor(
    private modalService: NgbModal,
    private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  public ngOnInit(): void {
    const request$ = this.httpClient.get<number>('/api/course/all/pages-count/20').pipe(
      filter(pagesCount => pagesCount !== 0),
      mergeMap(pagesCount => of([...Array(pagesCount).keys()])),
      mergeMap(pageIndex => this.httpClient.get<Course[]>(`/api/course/all?index=${pageIndex}&size=20`))
    );

    firstValueFrom(request$)
      .then(receivedCourses => {
        this.courses = receivedCourses;
        this.pagesCount = Math.ceil(receivedCourses.length / 20);
      })
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  public openCreateCourseModal() {
    this.modalService.open(AddCourseComponent);
  }

  public onSearchFormSubmit($event: SubmitEvent) {
    const request$ = this.httpClient.get<Course[]>(`/api/course/search?query=${encodeURIComponent(this.searchForm.value.query)}`);

    firstValueFrom(request$)
      .then(receivedCourses => {
        this.courses = receivedCourses;
        this.pagesCount = Math.ceil(receivedCourses.length / 20);
      })
      .finally(() => this.changeDetectorRef.detectChanges());

    $event.preventDefault();
  }

  public onCurrentPageChange(currentPageIndex: number) {
    this.pageIndex = currentPageIndex;
    this.changeDetectorRef.detectChanges();
  }

  public get currentPageCourses(): Course[] {
    return this.courses.slice(this.pageIndex * 20, this.pageIndex * 20 + 20);
  }

  onCourseDelete(course: Course) {
    this.courses = [...this.courses].filter(currentCourse => currentCourse.id !== course.id);
    this.changeDetectorRef.detectChanges();
  }
}
