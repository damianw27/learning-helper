import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangePasswordPage } from './change-password/change-password.page';
import { ForgetPasswordPage } from './forget-password/forget-password.page';
import { LoginPage } from './login/login.page';
import { RegisterPage } from './register/register.page';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '../../structures/forms/forms.module';

@NgModule({
  declarations: [
    ChangePasswordPage,
    ForgetPasswordPage,
    LoginPage,
    RegisterPage,
  ],
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
})
export class CommonPagesModule {}
