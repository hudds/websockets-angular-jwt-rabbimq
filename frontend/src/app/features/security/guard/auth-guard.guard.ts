import { Injectable, OnInit } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { SessionService, SessionStatus } from '../service/session.service';

@Injectable({
  providedIn:'root'
})
export class AuthGuard implements CanActivate {

  private sessionStatus : SessionStatus | null = null;

  constructor(private sessionService : SessionService, private router : Router){
    this.sessionService.sessionSubject.subscribe(sessionStatus => this.sessionStatus = sessionStatus)
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    console.log("auth guard")
      if(this.sessionService.getSessionStatus()?.status == 'AUTHENTICATED'){
        return true;
      }
      return this.router.createUrlTree(["/login"])

  }

}
