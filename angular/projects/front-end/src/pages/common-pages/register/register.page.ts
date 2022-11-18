import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { RegisterStore } from './register.store';
import { map, Observable } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
  providers: [RegisterStore],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterPage {
  public registrationForm = this.formBuilder.group({
    name: [''],
    displayName: [''],
    email: [''],
    password: [''],
    rePassword: [''],
    cityName: [''],
    country: [''],
    timezone: [''],
  });

  public validationErrors$ = this.store.selectValidationErrors;

  constructor(private formBuilder: FormBuilder, private store: RegisterStore) {}

  public getInputClassName(fieldName: string): Observable<string> {
    return this.validationErrors$.pipe(
      map(errors => errors[fieldName]),
      map(fieldErrors => fieldErrors !== undefined && fieldErrors.length !== 0),
      map(fieldHasErrors =>
        fieldHasErrors ? 'form-control is-invalid' : 'form-control'
      )
    );
  }
}
