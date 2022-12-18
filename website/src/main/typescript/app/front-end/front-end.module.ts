import { NgModule } from '@angular/core';
import { FrontEndComponent } from './front-end.component';
import { FrontEndRoutingModule } from './front-end-routing.module';
import { FrontEndStoreModule } from './store/front-end-store.module';
import { PagesModule } from './pages/pages.module';
import { ComponentsModule } from './components/components.module';
import { HttpClientModule } from '@angular/common/http';
import { AsyncPipe, NgIf } from '@angular/common';
import { QuillModule } from 'ngx-quill';

@NgModule({
	declarations: [FrontEndComponent],
	imports: [
		FrontEndRoutingModule,
		FrontEndStoreModule,
		HttpClientModule,
		ComponentsModule,
		PagesModule,
		AsyncPipe,
		NgIf,
		QuillModule.forRoot(),
	],
	exports: [FrontEndComponent],
})
export class FrontEndModule {}
