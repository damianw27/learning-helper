import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PageTitleComponent } from './page-title/page-title.component';
import { PageSmallBodyComponent } from './page-small-body/page-small-body.component';
import { FormStructureModule } from './form-structure/form-structure.module';
import { PageBodyComponent } from './page-body/page-body.component';
import { PageFooterComponent } from './page-footer/page-footer.component';
import { PageHeaderComponent } from './page-header/page-header.component';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { LoggedUserActionsComponent } from './logged-user-actions/logged-user-actions.component';
import { HeaderWithActionsComponent } from './header-with-actions/header-with-actions.component';

@NgModule({
  declarations: [
    PageTitleComponent,
    PageSmallBodyComponent,
    PageBodyComponent,
    PageFooterComponent,
    PageHeaderComponent,
    LoggedUserActionsComponent,
    HeaderWithActionsComponent,
  ],
  imports: [CommonModule, FormStructureModule, NgbDropdownModule, RouterModule],
  exports: [
    PageTitleComponent,
    PageSmallBodyComponent,
    PageBodyComponent,
    FormStructureModule,
    PageFooterComponent,
    PageHeaderComponent,
    HeaderWithActionsComponent,
  ],
})
export class StructureModule {}
