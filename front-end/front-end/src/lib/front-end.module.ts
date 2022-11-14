import { NgModule } from '@angular/core';
import { FrontEndComponent } from './front-end.component';
import { AppRoutingModule } from './app-routing.module';



@NgModule({
  declarations: [
    FrontEndComponent
  ],
  imports: [
  
    AppRoutingModule
  ],
  exports: [
    FrontEndComponent
  ]
})
export class FrontEndModule { }
