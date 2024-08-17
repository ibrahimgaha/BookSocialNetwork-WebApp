import { inject, Injectable } from '@angular/core';
import { CanActivate, Router  } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from '../token/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  canActivate() {
    const tokenService =inject(TokenService);
    const router = inject (Router);
    if(tokenService.isTokenNotValid()) {
      router.navigate(['login']);
      return false;
    }
    return true;
  }
  
}
