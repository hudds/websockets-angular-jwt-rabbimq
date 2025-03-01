import { Component } from '@angular/core';
import { AppUser } from '../../types/user';
import { SessionService } from 'src/app/features/security/service/session.service';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss'
})
export class TopbarComponent {
  user : AppUser | null = null

  constructor(private sessionService : SessionService) {

  }

  ngOnInit(): void {
    this.user =  this.sessionService.getUser()
    this.sessionService.userSubject.subscribe(user => {
      this.user = user
    })
  }

  get hasUser() : boolean {
    return this.user != null;
  }
  
}
