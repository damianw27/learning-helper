import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-sub-posts',
  templateUrl: './sub-posts.component.html',
  styleUrls: ['./sub-posts.component.scss'],
})
export class SubPosts implements OnInit {
  @Input() isEditMode: boolean = false;

  constructor(
    private httpClient: HttpClient,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.section === null) {
      return;
    }

    const request$ = this.httpClient.get<Lesson[]>(
      `/api/lesson/by-section/${this.section.id}`
    );

    firstValueFrom(request$)
      .then((receivedLessons) => this.onGetLessonsSuccess(receivedLessons))
      .catch(() => (this.lessons = []))
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  private onGetLessonsSuccess(receivedLessons: Lesson[]): void {
    this.lessons = receivedLessons.sort(
      (a, b) => (a.cardinalNumber ?? 0) - (b.cardinalNumber ?? 0)
    );
  }

  public dropLessonItem(event: CdkDragDrop<FormGroup[]>) {
    moveItemInArray(this.lessons, event.previousIndex, event.currentIndex);
    this.changeDetectorRef.detectChanges();
  }

  public onNewLessonAdd(lesson: Lesson): void {
    this.lessons.push(lesson);
    this.changeDetectorRef.detectChanges();
  }

  public onLessonDelete(lessonId: number): void {
    const request$ = this.httpClient.delete(`/api/lesson/${lessonId}`);

    firstValueFrom(request$)
      .then(() => this.removeLessonAndRefreshView(lessonId))
      .catch(console.error);
  }

  private removeLessonAndRefreshView(lessonId: number): void {
    this.lessons = [...this.lessons]
      .filter((lesson) => lesson.id !== lessonId)
      .sort((a, b) => (a.cardinalNumber ?? 0) - (b.cardinalNumber ?? 0));

    this.changeDetectorRef.detectChanges();
  }
}
