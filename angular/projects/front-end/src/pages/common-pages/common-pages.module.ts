import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangePasswordPage } from './change-password/change-password.page';
import { ForgetPasswordPage } from './forget-password/forget-password.page';
import { LoginPage } from './login/login.page';
import { RegisterPage } from './register/register.page';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComponentsModule } from '../../components/components.module';

@NgModule({
  declarations: [
    ChangePasswordPage,
    ForgetPasswordPage,
    LoginPage,
    RegisterPage,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    NgbModule,
    ComponentsModule,
  ],
})
export class CommonPagesModule {}
