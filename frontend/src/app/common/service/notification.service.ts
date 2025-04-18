import { Injectable } from '@angular/core';
import { BehaviorSubject, mergeMap } from 'rxjs';
import { ApiTokenService } from 'src/app/features/security/service/api-token.service';
import { environment } from 'src/environments/environment';
import {
  isUserNotification,
  UserNotification,
  UserNotificationTopic,
} from '../types/notification';
import { Client, StompHeaders } from '@stomp/stompjs';
import { Course, isCourse } from '../types/course';
import {
  isSubscriptionStatus,
  SubscriptionStatus,
} from 'src/app/features/subscription/types/subscription';
import { SessionService } from 'src/app/features/security/service/session.service';

@Injectable({
  providedIn: 'root',
})
export class UserNotificationService {
  private apiHost = environment.apiHost;

  private readonly _courseSubject = new BehaviorSubject<Course | null>(null);
  private readonly _subscriptionStatusSubject =
    new BehaviorSubject<SubscriptionStatus | null>(null);
  private client?: Client;
  private globalClient?: Client;
  constructor(
    private tokenService: ApiTokenService,
    private sessionService: SessionService
  ) {
    sessionService.sessionSubject.subscribe((session) => {
      if (session?.status == 'UNAUTHENTICATED') {
        this.disconnectGlobalClient();
        this.disconnectUserClient();
      }
    });
  }

  private disconnectUserClient() {
    this.client?.deactivate();
    this.client = undefined;
  }

  private disconnectGlobalClient() {
    this.globalClient?.deactivate();
    this.globalClient = undefined;
  }

  private connectUserNotification() {
    this.disconnectUserClient();
    let username = this.sessionService.getUser()?.username;
    if (username == undefined) {
      return;
    }
    console.log('stating stomp connection');
    let client = new Client({
      brokerURL: `ws://${this.apiHost}/events`,
      onConnect: () => {
        console.log('stomp connected');
        let headers: StompHeaders = {
          token: `${this.tokenService.getUserNotificationToken()}`,
        };
        client.subscribe(
          `/notification/user/${username}`,
          (message) => {
            this.publishMessageToSubjects(message.body);
          },
          headers
        );
      },
      onStompError: (e) => {
        console.log('stomp Error', e);
      },
      onWebSocketError: () => {
        console.log('WebSocketError');
      },
    });
    client.activate();
    this.client = client;
  }

  private connectGlobalNotification() {
    this.disconnectGlobalClient();
    console.log('stating stomp connection');
    let client = new Client({
      brokerURL: `ws://${this.apiHost}/events`,
      onConnect: () => {
        let headers: StompHeaders = {
          token: `${this.tokenService.getUserNotificationToken()}`,
        };
        client.subscribe(
          `/notification/global`,
          (message) => {
            this.publishMessageToSubjects(message.body);
          },
          headers
        );
      },
      onStompError: (e) => {
        console.log('stomp Error', e);
      },
      onWebSocketError: () => {
        console.log('WebSocketError');
      },
    });
    client.activate();
    this.client = client;
  }

  private publishMessageToSubjects(message: any) {
    console.log('notification  message received: ', message);
    let parcedMessage = JSON.parse(message);
    if (!isUserNotification(parcedMessage)) {
      return;
    }
    let notification = parcedMessage as UserNotification;
    if (
      notification.topic === UserNotificationTopic.COURSE_UPDATE &&
      isCourse(notification.content)
    ) {
      this._courseSubject.next(notification.content);
    } else if (
      notification.topic === UserNotificationTopic.SUBSCRIPTION_UPDATE &&
      isSubscriptionStatus(notification.content)
    ) {
      this._subscriptionStatusSubject.next(notification.content);
    }
  }

  private connect() {
    if (!this.client?.connected) {
      this.connectUserNotification();
    }
    if (!this.globalClient?.connected) {
      this.connectGlobalNotification();
    }
  }

  get courseSubject() {
    this.connect();
    return this._courseSubject;
  }

  get subscriptionStatusSubject() {
    this.connect();
    return this._subscriptionStatusSubject;
  }
}
