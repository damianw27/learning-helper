import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewPostsPageComponent } from './view-posts/view-posts-page.component';
import { ViewPostPageComponent } from './view-post/view-post-page.component';
import { ErrorPagesModule } from './error-pages/error-pages.module';
import { CommonPagesModule } from './common-pages/common-pages.module';
import { ViewSubPostPageComponent } from './view-sub-post/view-sub-post-page.component';
import { ComponentsModule } from '../components/components.module';
import { ReactiveFormsModule } from '@angular/forms';
import { QuillModule, QuillViewHTMLComponent } from 'ngx-quill';
import { StrictAsyncPipe } from '../pipe/strict-async';

@NgModule({
	declarations: [
		ViewPostsPageComponent,
		ViewPostPageComponent,
		ViewSubPostPageComponent,
	],
	imports: [
		CommonModule,
		ErrorPagesModule,
		CommonPagesModule,
		ComponentsModule,
		ReactiveFormsModule,
		QuillViewHTMLComponent,
		StrictAsyncPipe,
	],
	exports: [CommonPagesModule, ErrorPagesModule, QuillModule],
})
export class PagesModule {}
