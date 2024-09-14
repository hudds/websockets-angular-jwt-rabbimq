import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiTokenService } from '../../security/service/api-token.service';
import { SessionService } from '../../security/service/session.service';
import { AuthenticationResponse } from '../../security/types/authentication-response';
import { UserCredentials } from '../types/user-credentials';
import { environment } from 'src/environments/environment';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private api = environment.api

  constructor(private httpClient : HttpClient, private apiTokenService : ApiTokenService, private sessionService : SessionService) {}

  login(userCredentials : UserCredentials) : Observable<AuthenticationResponse> {
    return this.httpClient.post<AuthenticationResponse>(`${this.api}/user/auth`, userCredentials).pipe(
      tap((response : AuthenticationResponse) => {
        console.log("pipe setTokens: ", response)
        this.apiTokenService.setTokens(response)
      }),
      tap((response : AuthenticationResponse) => { 
        console.log("pipe sessionSubject next: ", response)
        this.sessionService.sessionSubject.next({status:'AUTHENTICATED', user:response.user})

      })
    )
  }
}
