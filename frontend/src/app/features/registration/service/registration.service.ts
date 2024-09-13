import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private api = environment.api
  
  constructor(private httpClient :  HttpClient ) { }


  
}
