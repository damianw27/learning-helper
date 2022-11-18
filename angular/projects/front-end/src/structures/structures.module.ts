import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PageBody } from './page-body/page-body.component';
import { PageFooter } from './page-footer/page-footer.component';
import { PageHeader } from './page-header/page-header.component';
import { PageSmallBody } from './page-small-body/page-small-body.component';
import { PageTitle } from './page-title/page-title.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [PageBody, PageFooter, PageHeader, PageTitle, PageSmallBody],
  exports: [
    PageBody,
    PageFooter,
    PageHeader,
    PageTitle,
    PageSmallBody,
    FormsModule,
  ],
  imports: [CommonModule, FormsModule],
})
export class StructuresModule {}
