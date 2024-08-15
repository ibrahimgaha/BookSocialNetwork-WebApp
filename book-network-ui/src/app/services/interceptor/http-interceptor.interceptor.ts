import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../token/token.service';

@Injectable()
export class HttpInterceptorInterceptor implements HttpInterceptor {

  constructor(private tokenService:TokenService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.tokenService.token;
    if(token){
      const authRequ=request.clone({
        headers: new HttpHeaders({
          Authorization:'Bearer ' + token
        })
      });
      return next.handle(authRequ);
    }
    return next.handle(request);
  }
}
