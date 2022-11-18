import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EMPTY, firstValueFrom, Observable, tap } from 'rxjs';
import { UserAuth } from '../model/user-auth.model';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { QuestionnaireComponent } from '../component/questionnaire/questionnaire.component';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private loggedUser?: User = undefined;
  private isQuestionnaireShown: boolean = false;

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private modalService: NgbModal
  ) {}

  public login(authorizationToken: string): Observable<UserAuth> {
    this.isQuestionnaireShown = false;

    const headers = new HttpHeaders({
      Authorization: `Basic ${authorizationToken}`,
      'Content-Type': 'application/x-www-form-urlencoded',
      'X-Requested-With': 'XMLHttpRequest',
    });

    return this.httpClient
      .get<UserAuth>('/api/user/login', { headers })
      .pipe(tap((response) => (this.loggedUser = response.user)));
  }

  public logout(): void {
    this.isQuestionnaireShown = false;

    if (this.loggedUser === undefined) {
      return;
    }

    const request$ = this.httpClient.post<void>('/api/user/logout', {}).pipe(
      tap(() => (this.loggedUser = undefined)),
      tap(() => this.router.navigateByUrl('/login'))
    );

    firstValueFrom(request$).then(() => this.router.navigateByUrl('/login'));
  }

  public isAuthenticated(): boolean {
    return this.loggedUser !== null;
  }

  public getLoggedUser(): User {
    if (this.loggedUser !== undefined) {
      console.log(this.loggedUser);

      if (
        this.loggedUser.learningStyle === null &&
        !this.isQuestionnaireShown
      ) {
        this.modalService.open(QuestionnaireComponent);
        this.isQuestionnaireShown = true;
      }

      return this.loggedUser;
    }

    this.router.navigateByUrl('/login');
    throw new Error('Session expired!');
  }

  public updateLoggedUserAfterQuestionnaire(user: User): void {
    this.loggedUser = user;
    this.isQuestionnaireShown = false;
  }
}
