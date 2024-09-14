import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { BehaviorSubject, catchError, map, Observable, of, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SessionService } from './session.service';
import { AuthenticationResponse } from '../types/authentication-response';

@Injectable({
  providedIn: 'root'
})
export class ApiTokenService {

  private api = environment.api;

  constructor(private sessionService : SessionService, private httpClient : HttpClient) { }


  public getBearerToken() : Observable <string | null> {
    let bearer = this.getBearertokenFromLocalStrorage()
    if(bearer == null){
      this.sessionService.sessionSubject.next({status:'UNAUTHENTICATED'})
      return of(null)
    }
    const decoded = jwtDecode(bearer);
    if(decoded.exp && new Date().getTime() > decoded.exp){
      return this.fetchNewToken()
    }
    return of(this.getBearertokenFromLocalStrorage());
  }

  private getBearertokenFromLocalStrorage() {
    return localStorage.getItem("bearerToken")
  }

  private fetchNewToken() : Observable<string | null> {
    const refreshToken = this.getRefreshToken()
    if(refreshToken == null){
      this.sessionService.sessionSubject.next({status:'UNAUTHENTICATED'})
      return of(null)
    }
    return this.httpClient
      .get<AuthenticationResponse>(`${this.api}/user/auth/refresh-token`, {headers:{"refresh-token":refreshToken}})
      .pipe(catchError(error => {
          if(error.status == 401){
            this.sessionService.sessionSubject.next({status:'UNAUTHENTICATED'})
            return of()
          }
          console.error(error)
          if(error.status && error.status > 500 && error.status < 600){
            return throwError(() => new Error("Server error"))
          }
          return throwError(() => new Error("Unknown error"))
      }),
      map((authenticationResponse : AuthenticationResponse) => {
        this.sessionService.sessionSubject.next({status:'AUTHENTICATED', user:authenticationResponse.user})
        this.setTokens(authenticationResponse)
        return this.getBearertokenFromLocalStrorage()
      })
    )
  }

  public setTokens(authenticationResponse : AuthenticationResponse){
    this.setBearerToken(authenticationResponse.accessToken)
    this.setRefreshToken(authenticationResponse.refreshToken)
  }

  private getRefreshToken(){
      localStorage.getItem("refreshToken");
  }

  private setRefreshToken(refreshToken : string) {
    localStorage.setItem("refreshToken", refreshToken) 
  }


  private setBearerToken(refreshToken : string) {
    localStorage.setItem("bearerToken", refreshToken) 
  }

}

