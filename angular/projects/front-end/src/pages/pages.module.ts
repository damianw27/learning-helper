import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ViewPostsPage} from './view-posts/view-posts.page';
import {ViewPostPage} from './view-post/view-post.page';
import {ErrorPagesModule} from './error-pages/error-pages.module';
import {CommonPagesModule} from './common-pages/common-pages.module';
import {ViewSubPostPage} from './view-sub-post/view-sub-post.page';
import {ComponentsModule} from "../components/components.module";

@NgModule({
  declarations: [ViewPostsPage, ViewPostPage, ViewSubPostPage],
  imports: [
    CommonModule,
    ErrorPagesModule,
    CommonPagesModule,
    ComponentsModule,
  ],
  exports: [CommonPagesModule, ErrorPagesModule],
})
export class PagesModule {
}
