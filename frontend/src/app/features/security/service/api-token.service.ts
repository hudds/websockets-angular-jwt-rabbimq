import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import {
  EMPTY,
  catchError,
  finalize,
  map,
  Observable,
  of,
  ReplaySubject,
  take,
  tap,
  throwError,
  retryWhen,
  retry,
  delay,
} from 'rxjs';
import { environment } from 'src/environments/environment';
import { SessionService } from './session.service';
import { AuthenticationResponse } from '../types/authentication-response';

@Injectable({
  providedIn: 'root',
})
export class ApiTokenService {
  private api = environment.api;
  private refreshingToken = false;
  private refreshTokenObservable = new ReplaySubject<string | null>(1);

  constructor(
    private sessionService: SessionService,
    private httpClient: HttpClient
  ) {}

  public revokeRefreshTokenAndClearStorage(): Observable<void> {
    let refreshToken = this.getRefreshToken();
    if (refreshToken == null) {
      return EMPTY;
    }
    return this.httpClient
      .delete<void>(`${this.api}/user/auth/refresh-token`, {
        headers: { 'refresh-token': refreshToken },
      })
      .pipe(
        finalize(() => {
          this.clearLocalStorage();
        })
      );
  }

  public getBearerToken(): Observable<string | null> {
    const operationId = new Date().getTime();
    let bearer = this.getBearertokenFromLocalStrorage();
    if (bearer == null) {
      this.sessionService.sessionSubject.next({ status: 'UNAUTHENTICATED' });
      return of(null);
    }
    const decoded = jwtDecode(bearer);
    if (decoded.exp && new Date().getTime() > decoded.exp * 1000) {
      return this.fetchNewToken();
    }
    return of(this.getBearertokenFromLocalStrorage());
  }

  private getBearertokenFromLocalStrorage() {
    return localStorage.getItem('bearerToken');
  }

  private fetchNewToken(): Observable<string | null> {
    if (this.refreshingToken) {
      return this.refreshTokenObservable.pipe(take(1));
    }
    this.refreshingToken = true;
    this.refreshTokenObservable.complete();
    this.refreshTokenObservable = new ReplaySubject(1);
    let refreshToken = this.getRefreshToken();
    if (refreshToken == null) {
      this.sessionService.sessionSubject.next({ status: 'UNAUTHENTICATED' });
      return of(null);
    }
    this.httpClient
      .get<AuthenticationResponse>(`${this.api}/user/auth/refresh-token`, {
        headers: { 'refresh-token': refreshToken },
      })
      .pipe(
        catchError((error) => {
          if (error.status == 401) {
            this.sessionService.sessionSubject.next({
              status: 'UNAUTHENTICATED',
            });
            return of();
          }
          console.error(error);
          if (error.status && error.status > 500 && error.status < 600) {
            return throwError(
              () => new Error('Server error. Status code: ' + error.status)
            );
          }
          return throwError(() => new Error('Unknown error'));
        }),
        map((authenticationResponse: AuthenticationResponse) => {
          this.sessionService.sessionSubject.next({
            status: 'AUTHENTICATED',
            user: authenticationResponse.user,
          });
          this.setTokens(authenticationResponse);
          return this.getBearertokenFromLocalStrorage();
        }),
        finalize(() => (this.refreshingToken = false))
      )
      .subscribe((token) => {
        this.refreshTokenObservable.next(token);
      });
    return this.refreshTokenObservable.pipe(take(1));
  }

  public setTokens(authenticationResponse: AuthenticationResponse) {
    this.setBearerToken(authenticationResponse.accessToken);
    this.setRefreshToken(authenticationResponse.refreshToken);
    this.setUserNotificationToken(authenticationResponse.userNotificationToken);
  }

  private clearLocalStorage() {
    localStorage.clear();
  }

  private getRefreshToken() {
    return localStorage.getItem('refreshToken');
  }

  private setRefreshToken(refreshToken: string) {
    localStorage.setItem('refreshToken', refreshToken);
  }

  private setBearerToken(refreshToken: string) {
    localStorage.setItem('bearerToken', refreshToken);
  }

  private setUserNotificationToken(userNotificationToken: string) {
    localStorage.setItem('userNotificationToken', userNotificationToken);
  }

  public getUserNotificationToken(): string | null {
    return localStorage.getItem('userNotificationToken');
  }
}
