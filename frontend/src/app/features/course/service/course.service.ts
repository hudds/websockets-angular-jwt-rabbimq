import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, mergeMap, Observable } from 'rxjs';
import { Course } from 'src/app/common/types/course';
import { environment } from 'src/environments/environment';
import { ApiTokenService } from '../../security/service/api-token.service';
import { SessionService } from '../../security/service/session.service';
import { Client } from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private apiHost = environment.apiHost

  private readonly _courseSubject = new BehaviorSubject<Course | null>(null);

  private client?: Client

  constructor(private httpClient: HttpClient, private tokenService: ApiTokenService, private sessionService: SessionService) {
    sessionService.sessionSubject.subscribe(session => {
      if (session?.status == 'AUTHENTICATED' && !this.client?.connected) {
        this.connectToCourseTopic()
      } else if (session?.status == 'UNAUTHENTICATED') {
        this.disconnectFromCourseTopic()
      }
    })
    if(sessionService.getSessionStatus()?.status == 'AUTHENTICATED' && !this.client?.connected){
      this.connectToCourseTopic()
    }
  }

  disconnectFromCourseTopic() {
    this.client?.deactivate()
    this.client = undefined
  }
  connectToCourseTopic() {
    this.disconnectFromCourseTopic()
    console.log("stating stomp connection")
    this.tokenService.getBearerToken().subscribe(token => {
      let client = new Client({
        brokerURL: `ws://${this.apiHost}/events`,
        connectHeaders: {
          Authorization: `Bearer ${token}`
        },
        onConnect: () => {
          console.log("connected stomp")
          client.subscribe('/topic/course', message => {
            console.log(`Received course: ${message.body}`)
            this._courseSubject.next(JSON.parse(message.body))
          });
        },
        onStompError: (e) => {
          console.log("stomp Error", e)
        },
        onWebSocketError: () => {
          console.log("WebSocketError")
        }
      })
      client.activate()
      this.client = client;
    });
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
    if(!this.client?.connected){
      this.connectToCourseTopic()
    }
    return this._courseSubject;
  }
}
