import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiTokenService } from '../../security/service/api-token.service';
import { mergeMap, Observable, Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SubscriptionStatus } from '../types/subscription';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private readonly api = environment.api;

  constructor(private httpClient: HttpClient, private tokenService: ApiTokenService) { }

  public subscribeToCourse(courseId: number): Observable<SubscriptionStatus> {
    return this.tokenService.getBearerToken().pipe(
      mergeMap(token => {
        console.log("token: ", token)
        return this.httpClient.post<SubscriptionStatus>(`${this.api}/subscription/course/${courseId}`, null, {
          headers: {
            "Authorization": `Bearer ${token}`
          }
        })
      })
    )
  }
}
