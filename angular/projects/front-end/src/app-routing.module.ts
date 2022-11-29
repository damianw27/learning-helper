import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundPage } from './pages/error-pages/not-found/not-found.page';
import { LoginPage } from './pages/common-pages/login/login.page';
import { RegisterPage } from './pages/common-pages/register/register.page';
import { ForgetPasswordPage } from './pages/common-pages/forget-password/forget-password.page';
import { ChangePasswordPage } from './pages/common-pages/change-password/change-password.page';
import { ServerIssuePage } from './pages/error-pages/server-issue/server-issue.page';
import {ViewPostsPage} from "./pages/view-posts/view-posts.page";
import {ViewPostPage} from "./pages/view-post/view-post.page";
import {PagesModule} from "./pages/pages.module";
import {ComponentsModule} from "./components/components.module";

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
    component: LoginPage,
  },
  {
    path: 'register',
    component: RegisterPage,
  },
  {
    path: 'forget-password',
    component: ForgetPasswordPage,
  },
  {
    path: 'change-password',
    component: ChangePasswordPage,
  },
  {
    path: 'error',
    component: ServerIssuePage,
  },
  {
    path: 'posts',
    component: ViewPostsPage,
  },
  {
    path: 'view-post/:id',
    component: ViewPostPage,
  },
  {
    path: '**',
    component: NotFoundPage,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
