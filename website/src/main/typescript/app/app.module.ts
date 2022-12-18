import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { XhrInterceptorService } from './service/xhr-interceptor.service';
import { DefaultUserService } from './api/default-user.service';
import { FrontEndModule } from './front-end/front-end.module';
import { DefaultFileService } from './api/default-file.service';
import { DefaultPostService } from './api/default-post.service';
import { DefaultQuestionService } from './api/default-question.service';
import { DefaultSubPostService } from './api/default-sub-post.service';

@NgModule({
	declarations: [AppComponent],
	imports: [
		BrowserModule,
		AppRoutingModule,
		FrontEndModule,
		HttpClientModule,
		NgbModule,
	],
	providers: [
		DefaultFileService,
		DefaultPostService,
		DefaultQuestionService,
		DefaultSubPostService,
		DefaultUserService,
		{
			provide: HTTP_INTERCEPTORS,
			useClass: XhrInterceptorService,
			multi: true,
		},
	],
	bootstrap: [AppComponent],
})
export class AppModule {}
