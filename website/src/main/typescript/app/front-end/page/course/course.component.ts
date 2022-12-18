import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnInit,
} from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CoursesApiService } from '../../services/courses-api.service';
import { Course } from '../../model/course.model';
import { firstValueFrom } from 'rxjs';
import { User } from '../../model/user.model';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateExamComponent } from './component/modals/create-exam/create-exam.component';
import { IncomingExamsComponent } from './component/modals/incoming-exams/incoming-exams.component';
import { ParticipantsComponent } from './component/modals/participants/participants.component';
import { CreateQuestionComponent } from './component/modals/create-question/create-question.component';
import { Question } from '../../model/question.model';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CourseComponent implements OnInit {
  public course!: Course;
  public loggedInUser!: User;
  public questions: Question[] = [];
  public items: string[] = [];

  public courseEditForm = this.formBuilder.group({
    id: [-1, [Validators.required]],
    title: ['', [Validators.required]],
    description: [''],
    tags: [[]],
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

  public ngOnInit(): void {
    this.loggedInUser = this.authService.getLoggedUser();

    firstValueFrom(this.activatedRoute.params)
      .then((params) => this.loadCourse(params))
      .catch(() => this.router.navigateByUrl('/courses'));
  }

  public getDescriptionFormControl(): FormControl {
    return <FormControl>this.courseEditForm.controls['description'];
  }

  public saveCourseChanges(): void {
    const request$ = this.coursesApiService.updateCourse(
      this.courseEditForm.value
    );

    firstValueFrom(request$)
      .then((responseCourse) => (this.course = responseCourse))
      .finally(() => this.changeDetectorRef.detectChanges());
  }

  public openCreateExamModal(): void {
    const createExamModalRef = this.modalService.open(CreateExamComponent);
    createExamModalRef.componentInstance.courseId = this.course.id;
  }

  public openExamsModal(): void {
    const modalRef = this.modalService.open(IncomingExamsComponent, {
      size: 'xl',
    });
    modalRef.componentInstance.courseId = this.course.id;
  }

  public openParticipantsModal(): void {
    const modalRef = this.modalService.open(ParticipantsComponent);
    modalRef.componentInstance.courseId = this.course.id;
  }

  public openCreateQuestionModal(): void {
    const modalRef = this.modalService.open(CreateQuestionComponent);
    modalRef.componentInstance.courseId = this.course.id;
    modalRef.componentInstance.questionCreated.subscribe(
      (createdQuestion: Question) => {
        this.questions.push(createdQuestion);
        this.changeDetectorRef.detectChanges();
      }
    );
  }

  public get tagsFormControl(): FormControl {
    return <FormControl>this.courseEditForm.controls['tags'];
  }

  private loadCourse(params: Params) {
    const courseId = +params['id'];

    if (isNaN(courseId)) {
      throw new Error('Invalid course id!');
    }

    firstValueFrom(this.courseApi.getCourse(courseId)).then((responseCourse) =>
      this.onCourseLoadSuccess(responseCourse)
    );
  }

  private onCourseLoadSuccess(responseCourse: Course) {
    if (responseCourse === null) {
      throw new Error('Request returned empty course!');
    }

    this.courseEditForm.setValue({
      id: responseCourse.id,
      title: responseCourse.title,
      description: responseCourse.description.content,
      tags: responseCourse.tags,
    });

    if (responseCourse.author.id === this.authService.getLoggedUser().id) {
      const request$ = this.httpClient.get<Question[]>(
        `/api/question/${responseCourse.id}`
      );

      firstValueFrom(request$)
        .then((receivedQuestions) => (this.questions = receivedQuestions))
        .catch(console.error);
    }

    this.course = responseCourse;
    this.changeDetectorRef.detectChanges();
  }

  onDeleteCourse() {
    const request$ = this.httpClient.delete(`/api/course/${this.course.id}`);

    firstValueFrom(request$).then(() => this.router.navigateByUrl('/courses'));
  }
}
