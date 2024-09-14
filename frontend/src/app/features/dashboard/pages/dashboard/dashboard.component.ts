import { Component, OnInit } from '@angular/core';
import { AppUser } from 'src/app/common/types/user';
import { SessionService } from 'src/app/features/security/service/session.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit{

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
