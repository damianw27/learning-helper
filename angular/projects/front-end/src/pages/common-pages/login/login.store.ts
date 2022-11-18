import { Injectable } from '@angular/core';
import { ComponentStore } from '@ngrx/component-store';
import { AuthService } from '../../services/auth.service';
import { catchError, EMPTY, filter, Observable, switchMap, tap } from 'rxjs';
import { Router } from '@angular/router';

interface LoginState {
  invalidCredentials: boolean;
}

@Injectable()
export class LoginStore extends ComponentStore<LoginState> {
  constructor(
    private authorizationService: AuthService,
    private router: Router
  ) {
    super({
      invalidCredentials: false,
    });
  }

  readonly loginWithCredentials = this.effect((tokens$: Observable<string>) =>
    tokens$.pipe(
      tap(() => this.setCredentialsAreInvalid(false)),
      switchMap((token) => this.authorizationService.login(token)),
      filter((response) => response.authenticated),
      tap(() => this.router.navigateByUrl('/')),
      catchError((response) => {
        this.setCredentialsAreInvalid(true);
        return EMPTY;
      })
    )
  );

  readonly setCredentialsAreInvalid = this.updater(
    (state, invalidCredentials: boolean) => ({ ...state, invalidCredentials })
  );

  readonly selectCredentialsAreInvalid = this.select(
    (state) => state.invalidCredentials
  );
}
