import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ObjectUtils } from 'src/app/common/lib/utils/object-utils';
import { FlashMessageService } from 'src/app/common/service/flash-message.service';
import { LoginFormField, LoginFormService } from '../../service/login-form.service';
import { LoginService } from '../../service/login.service';
import { AppErrorHandler } from 'src/app/common/lib/error-resolver';
import { APIErrorType } from 'src/app/common/types/api-error';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  public readonly LoginFormField = LoginFormField;

  registrationMessage = ""

  constructor(
    private flashMessages : FlashMessageService,
    private _snackBar : MatSnackBar, 
    private loginForm : LoginFormService, 
    private loginService : LoginService,
    private router : Router
  ){}


  ngOnInit(): void {
    let message = this.flashMessages.getMessage("registrationMessage")
    if(ObjectUtils.isString(message)){
      this._snackBar.open(message, "Ok")
    }
  }


  login(){
    const userCredentials =  this.loginForm.getUserCredentials();
    let errorHandler = new AppErrorHandler(this._snackBar, this.loginForm);
    errorHandler.expectedApiErrors.push(APIErrorType.UNAUTHORIZED)

    if(userCredentials != null){
      this.loginService.login(userCredentials).subscribe({
        next:(user) => {
          console.log(user)
          this.router.navigate(['dashboard'])
        },
        error: (error) => {errorHandler.handle(error)}
      })
    }
  }

  get fieldErrorReolver(){
    return this.loginForm.errorResolver;
  }

  get formGroup(){
    return this.loginForm.formGroup
  }
}
