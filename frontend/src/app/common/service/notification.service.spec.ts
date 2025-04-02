import { TestBed } from '@angular/core/testing';

import { UserNotificationService } from './notification.service';

describe('SseNotificationService', () => {
  let service: UserNotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserNotificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
