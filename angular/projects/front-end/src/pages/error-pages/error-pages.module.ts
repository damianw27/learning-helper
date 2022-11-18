import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotFoundPage } from './not-found/not-found.page';
import { ServerIssuePage } from './server-issue/server-issue.page';

@NgModule({
  declarations: [NotFoundPage, ServerIssuePage],
  imports: [CommonModule],
})
export class ErrorPagesModule {}
