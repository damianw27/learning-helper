import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CoursesApiService } from '../../services/courses-api.service';
import { AuthService } from '../../services/auth.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { Lesson } from '../../model/lesson.model';
import { User } from '../../model/user.model';
import { firstValueFrom } from 'rxjs';
import { Course } from '../../model/course.model';
import { LessonContent } from '../../model/lesson-content.model';

@Component({
  selector: 'app-lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.scss'],
})
export class LessonComponent implements OnInit {
  lesson!: Lesson;
  course!: Course;
  loggedInUser!: User;
  isEditMode: boolean = false;

  lessonForm = this.formBuilder.group({
    id: [0, Validators.required],
    sectionId: [0, Validators.required],
    title: ['', Validators.required],
    cardinalNumber: [0, Validators.required],
    lessonContents: this.formBuilder.array([]),
  });

  constructor(
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private courseApi: CoursesApiService,
    private router: Router,
    private changeDetectorRef: ChangeDetectorRef,
    private authService: AuthService,
    private coursesApiService: CoursesApiService,
    private modalService: NgbModal,
    private httpClient: HttpClient
  ) {}

  ngOnInit(): void {
    this.loggedInUser = this.authService.getLoggedUser();

    firstValueFrom(this.activatedRoute.params)
      .then((params) => this.loadLesson(params))
      .catch(() => this.router.navigateByUrl('/courses'));
  }

  getContentFromFormGroup(formGroup: FormGroup): FormControl {
    return <FormControl>formGroup.controls['content'];
  }

  private loadLesson(params: Params) {
    const lessonId = +params['id'];

    if (isNaN(lessonId)) {
      throw new Error('Invalid lesson id!');
    }

    const request$ = this.httpClient.get<Lesson>(`/api/lesson/${lessonId}`);

    firstValueFrom(request$).then((responseCourse) =>
      this.onLessonLoadSuccess(responseCourse)
    );
  }

  private onLessonLoadSuccess(responseLesson: Lesson) {
    if (responseLesson === null || responseLesson === undefined) {
      throw new Error('Request returned empty course!');
    }

    this.lessonForm.setValue({
      id: responseLesson.id,
      sectionId: responseLesson.section.id,
      title: responseLesson.title,
      cardinalNumber: responseLesson.cardinalNumber,
      lessonContents: [],
    });

    responseLesson.lessonContents
      .map((lessonContent) => this.createLessonContentGroup(lessonContent))
      .forEach((lessonContentGroup) =>
        this.lessonContents.push(lessonContentGroup)
      );

    this.lesson = responseLesson;

    const request$ = this.httpClient.get<Course>(
      `/api/course/${responseLesson.section.courseId}`
    );

    firstValueFrom(request$)
      .then((receivedCourse) => (this.course = receivedCourse))
      .then(
        (receivedCourse) =>
          (this.isEditMode = receivedCourse.author.id === this.loggedInUser.id)
      )
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  private createLessonContentGroup(lessonContent: LessonContent): FormGroup {
    return this.formBuilder.group({
      id: [lessonContent.id],
      learningStyle: [lessonContent.learningStyle, Validators.required],
      content: [lessonContent.content.content, Validators.required],
    });
  }

  get lessonContents(): FormArray {
    return <FormArray>this.lessonForm.controls['lessonContents'];
  }

  get lessonContentsGroups(): FormGroup[] {
    return <FormGroup[]>this.lessonContents.controls;
  }

  addNewContentFormGroup() {
    const formGroup = this.formBuilder.group({
      id: [-1],
      learningStyle: ['', Validators.required],
      content: ['', Validators.required],
    });

    this.lessonContents.push(formGroup);
    this.changeDetectorRef.detectChanges();
  }

  onSubmit($event: SubmitEvent) {
    const request$ = this.httpClient.post(
      `/api/lesson/update`,
      this.lessonForm.value
    );

    firstValueFrom(request$).catch(console.log);

    $event.preventDefault();
  }

  get contents(): LessonContent[] {
    return this.lesson.lessonContents.filter(
      (lessonContent) =>
        lessonContent.learningStyle === this.loggedInUser.learningStyle
    );
  }
}
