import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { catchError, Observable } from 'rxjs';
import { AppUser } from 'src/app/common/types/user';
import { UserRegistrationData } from '../types/user-registration-data';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private api = environment.api
  
  constructor(private httpClient :  HttpClient ) { }



  registrate(userRegistrationData : UserRegistrationData) : Observable<AppUser> {
    console.log(`${this.api}/user/registration`)
    console.log(userRegistrationData)
    return this.httpClient.post<AppUser>(`${this.api}/user/registration`, userRegistrationData)
  }
  
}
