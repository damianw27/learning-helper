import { Injectable } from '@angular/core';
import { ComponentStore } from '@ngrx/component-store';
import { catchError, EMPTY, Observable, switchMap, tap } from 'rxjs';
import { ValidationErrors } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

interface RegisterState {
  validationErrors: Record<string, string[]>;
}

@Injectable()
export class RegisterStore extends ComponentStore<RegisterState> {
  constructor(private httpClient: HttpClient, private router: Router) {
    super({
      validationErrors: {},
    });
  }

  readonly selectValidationErrors = this.select(state => state.validationErrors);

  readonly setValidationErrors = this.updater((state, validationErrors: ValidationErrors) => ({
    ...state,
    validationErrors,
  }));

  readonly registerUser = this.effect((registerFormData$: Observable<Record<string, string>>) => {
    return registerFormData$.pipe(
      tap(() => this.setValidationErrors({})),
      switchMap(formData => this.requestUserRegistration(formData))
    );
  });

  private readonly requestUserRegistration = (registrationFormData: Record<string, string>) =>
    this.httpClient.post('/api/user/create', registrationFormData).pipe(
      tap(() => this.router.navigateByUrl('/login')),
      catchError((response: HttpErrorResponse) => {
        this.setValidationErrors(response.error);
        return EMPTY;
      })
    );
}
