import { Component, inject } from '@angular/core';
import { RegistrationFormField, RegistrationFormService } from '../../service/registration-form.service';
import { RegistrationService } from '../../service/registration.service';
import { isAPIError } from 'src/app/common/types/api-error';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AppErrorHandler } from 'src/app/common/lib/error-resolver';
import { FlashMessageService } from 'src/app/common/service/flash-message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent {

  public readonly RegistrationFormField = RegistrationFormField;

  private _snackBar = inject(MatSnackBar);

  constructor(private router : Router, private registrationForm : RegistrationFormService, private registrationService : RegistrationService, private flashMessages : FlashMessageService) {}

  get formGroup(){
    return this.registrationForm.formGroup;
  }

  get errorResolver(){
    return this.registrationForm.fieldErrorResolver;
  }


  doRegistration(){
    if(!this.registrationForm.formGroup.valid){
      return;
    }
    let errorHandler = new AppErrorHandler(this._snackBar, this.registrationForm)
    let userRegistrationData = this.registrationForm.getUserRegistrationData();
    this.registrationService.registrate(userRegistrationData).subscribe({
      next: appUser => {
        this.flashMessages.putMessage("registrationMessage", "Usuário cadastrado com sucesso!");
        this.router.navigate(["/login"]);
      },
      error: error => {
        errorHandler.handle(error)
      }
    })
  }

}
