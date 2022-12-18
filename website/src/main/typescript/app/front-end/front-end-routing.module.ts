import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundPageComponent } from './pages/error-pages/not-found/not-found-page.component';
import { LoginPageComponent } from './pages/common-pages/login/login-page.component';
import { RegisterPageComponent } from './pages/common-pages/register/register-page.component';
import { ForgetPasswordPageComponent } from './pages/common-pages/forget-password/forget-password-page.component';
import { ChangePasswordPageComponent } from './pages/common-pages/change-password/change-password-page.component';
import { ServerIssuePageComponent } from './pages/error-pages/server-issue/server-issue-page.component';
import { ViewPostsPageComponent } from './pages/view-posts/view-posts-page.component';
import { ViewPostPageComponent } from './pages/view-post/view-post-page.component';

const routes: Routes = [
	{
		path: '',
		redirectTo: '/posts',
		pathMatch: 'full',
	},
	{
		path: 'home',
		redirectTo: '/posts',
		pathMatch: 'full',
	},
	{
		path: 'login',
		component: LoginPageComponent,
	},
	{
		path: 'register',
		component: RegisterPageComponent,
	},
	{
		path: 'forget-password',
		component: ForgetPasswordPageComponent,
	},
	{
		path: 'change-password',
		component: ChangePasswordPageComponent,
	},
	{
		path: 'error',
		component: ServerIssuePageComponent,
	},
	{
		path: 'posts',
		component: ViewPostsPageComponent,
	},
	{
		path: 'view-post/:id',
		component: ViewPostPageComponent,
	},
	{
		path: '**',
		component: NotFoundPageComponent,
	},
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule],
})
export class FrontEndRoutingModule {}
