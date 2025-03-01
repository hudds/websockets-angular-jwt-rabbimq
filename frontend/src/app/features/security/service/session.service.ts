import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { AppUser } from 'src/app/common/types/user';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private static idCounter = 1;

  private instanceId = SessionService.idCounter++

  public readonly sessionSubject = new BehaviorSubject<SessionStatus|null>(null);

  public readonly userSubject = new BehaviorSubject<AppUser|null>(null);

  constructor() {
    this.sessionSubject.subscribe((session) => {
      this.setSessionStatus(session)
    })
  }

  public getSessionStatus() : SessionStatus | null {
    const loggedUserData = localStorage.getItem("sessionStatus");
    if(loggedUserData != null){
      return JSON.parse(loggedUserData)
    }
    return null
  }

  public setSessionStatus(sessionStatus : SessionStatus | null) : void {
    if(sessionStatus){
      console.log("sessionStatus changed to: ", sessionStatus.status)
      localStorage.setItem("sessionStatus", JSON.stringify(sessionStatus));
    }
    this.userSubject.next(this.getSessionStatus()?.user ?? null)
  }

  public getUser() {
    return this.getSessionStatus()?.user ?? null
  }
}


export interface SessionStatus {
  status: "UNAUTHENTICATED" | "AUTHENTICATED",

  user?: AppUser
}
