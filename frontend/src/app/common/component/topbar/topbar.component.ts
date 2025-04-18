import { ApiTokenService } from 'src/app/features/security/service/api-token.service';
import { Component } from '@angular/core';
import { AppUser } from '../../types/user';
import { SessionService } from 'src/app/features/security/service/session.service';
import { catchError, finalize } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss'
})
export class TopbarComponent {
  user : AppUser | null = null

  constructor(private sessionService : SessionService, private apiTokenService : ApiTokenService, private router: Router) {

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

  public logout(){
    this.apiTokenService.revokeRefreshTokenAndClearStorage()
    .pipe(finalize(() => {
      this.router.navigate(['login'])
    }))
    .subscribe()
  }

}
