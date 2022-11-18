import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewPostsPage } from './view-posts/view-posts.page';
import { ViewPostPage } from './view-post/view-post.page';
import { StructuresModule } from '../structures/structures.module';
import { FormsModule } from '../structures/forms/forms.module';
import { ErrorPagesModule } from './error-pages/error-pages.module';
import { CommonPagesModule } from './common-pages/common-pages.module';
import { ViewSubPostPage } from './view-sub-post/view-sub-post.page';

@NgModule({
  declarations: [ViewPostsPage, ViewPostPage, ViewSubPostPage],
  imports: [
    CommonModule,
    StructuresModule,
    FormsModule,
    ErrorPagesModule,
    CommonPagesModule,
  ],
  exports: [CommonPagesModule, ErrorPagesModule],
})
export class PagesModule {}
