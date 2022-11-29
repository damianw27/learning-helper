import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {AppStoreModule} from './store/app-store.module';
import {PagesModule} from "./pages/pages.module";
import {ComponentsModule} from "./components/components.module";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [AppComponent],
  imports: [AppRoutingModule, AppStoreModule, HttpClientModule, ComponentsModule, PagesModule],
  exports: [AppComponent],
})
export class FrontEndModule {
}
