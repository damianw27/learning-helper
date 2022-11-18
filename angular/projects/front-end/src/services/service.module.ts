import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { XhrInterceptorService } from './xhr-interceptor.service';
import { AuthService } from './auth.service';

@NgModule({
  declarations: [],
  imports: [CommonModule],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: XhrInterceptorService,
      multi: true,
    },
  ],
})
export class ServiceModule {}
