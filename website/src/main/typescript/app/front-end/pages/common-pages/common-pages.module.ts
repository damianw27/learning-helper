import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangePasswordPageComponent } from './change-password/change-password-page.component';
import { ForgetPasswordPageComponent } from './forget-password/forget-password-page.component';
import { LoginPageComponent } from './login/login-page.component';
import { RegisterPageComponent } from './register/register-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComponentsModule } from '../../components/components.module';

@NgModule({
	declarations: [
		ChangePasswordPageComponent,
		ForgetPasswordPageComponent,
		LoginPageComponent,
		RegisterPageComponent,
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
