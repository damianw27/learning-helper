import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { LoginStore } from './login.store';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [LoginStore],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent implements OnInit {
  public loginForm = this.formBuilder.group({
    login: ['', Validators.required],
    password: ['', [Validators.required]],
    rememberLogin: [false],
  });

  public credentialsAreInvalid: Observable<boolean> = this.loginStore.selectCredentialsAreInvalid;

  constructor(private formBuilder: FormBuilder, private loginStore: LoginStore) {}

  public ngOnInit(): void {
    const nameFromStorage = localStorage.getItem('exmntr-login-form-username');

    this.loginForm.setValue({
      login: nameFromStorage ?? '',
      password: '',
      rememberLogin: nameFromStorage !== null,
    });
  }

  public onLoginFormSubmit(event: SubmitEvent): void {
    const token = this.getTokenFromLoginForm(this.loginForm);
    this.saveNameIfRememberName();
    this.loginStore.loginWithCredentials(token);
    event.preventDefault();
  }

  private getTokenFromLoginForm(loginForm: FormGroup): string {
    const login = loginForm.controls['login'].value;
    const password = loginForm.controls['password'].value;
    return btoa(`${login}:${password}`);
  }

  private saveNameIfRememberName(): void {
    const rememberLogin = this.loginForm.controls['rememberLogin'].value;

    if (!rememberLogin) {
      return;
    }

    const name = this.loginForm.controls['login'].value;
    localStorage.setItem('exmntr-login-form-username', name);
  }
}
