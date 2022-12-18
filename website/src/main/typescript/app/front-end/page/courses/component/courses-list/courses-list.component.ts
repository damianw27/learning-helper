import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { CoursesListStore } from './courses-list.store';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.scss'],
  providers: [CoursesListStore],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CoursesListComponent implements OnInit {
  public courses$ = this.coursesListStore.selectCourses;
  public pagesCount$ = this.coursesListStore.selectPagesCount;
  public currentPage$ = this.coursesListStore.selectCurrentPage;

  constructor(private coursesListStore: CoursesListStore) {}

  public ngOnInit(): void {
    this.coursesListStore.loadCourses();
  }

  public onCurrentPageChange(pageIndex: number): void {
    this.coursesListStore.setCurrentPage(pageIndex);
  }
}
