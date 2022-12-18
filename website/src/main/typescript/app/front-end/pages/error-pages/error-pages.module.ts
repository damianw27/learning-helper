import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotFoundPageComponent } from './not-found/not-found-page.component';
import { ServerIssuePageComponent } from './server-issue/server-issue-page.component';
import { RouterModule } from '@angular/router';

@NgModule({
	declarations: [NotFoundPageComponent, ServerIssuePageComponent],
	imports: [CommonModule, RouterModule],
})
export class ErrorPagesModule {}
