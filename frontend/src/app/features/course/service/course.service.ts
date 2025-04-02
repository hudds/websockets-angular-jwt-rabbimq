import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, mergeMap, Observable } from 'rxjs';
import { Course } from 'src/app/common/types/course';
import { environment } from 'src/environments/environment';
import { ApiTokenService } from '../../security/service/api-token.service';
import { SessionService } from '../../security/service/session.service';
import { Client, StompHeaders } from '@stomp/stompjs';
import { UserNotificationService } from 'src/app/common/service/notification.service';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private apiHost = environment.apiHost

  constructor(private httpClient: HttpClient, private tokenService: ApiTokenService, private notificationService : UserNotificationService) {
  
  }

  public getCourses(pageNumber?: number, pageSize?: number): Observable<Course[]> {
    let params = new HttpParams()
    if (pageNumber) {
      params = params.set("pageNumber", pageNumber.toString());
    }
    if (pageSize) {
      params = params.set("pageSize", pageSize.toString());
    }
    return this.tokenService.getBearerToken().pipe(
      mergeMap(token => {
        return this.httpClient.get<Course[]>(`http://${this.apiHost}/course`, {
          headers: {
            "Authorization": `Bearer ${token}`
          },
          params:params
        })
      })
    )
  }

  get courseSubject() : BehaviorSubject<Course | null>{
    return this.notificationService.courseSubject;
  }
}
