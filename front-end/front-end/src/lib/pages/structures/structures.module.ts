import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HeaderWithActionsComponent} from "./header-with-actions/header-with-actions.component";
import {LoggedUserActionsComponent} from "./logged-user-actions/logged-user-actions.component";
import {PageBodyComponent} from "./page-body/page-body.component";
import {PageFooterComponent} from "./page-footer/page-footer.component";
import {PageHeaderComponent} from "./page-header/page-header.component";
import {PageSmallBodyComponent} from "./page-small-body/page-small-body.component";
import {PageTitleComponent} from "./page-title/page-title.component";



@NgModule({
  declarations: [
    PageBodyComponent,
    PageFooterComponent,
    PageHeaderComponent,
    PageTitleComponent,
    PageSmallBodyComponent,
  ],
  exports: [
    PageBodyComponent,
    PageFooterComponent,
    PageHeaderComponent,
    PageTitleComponent,
    PageSmallBodyComponent,
  ],
  imports: [
    CommonModule
  ]
})
export class StructuresModule { }
