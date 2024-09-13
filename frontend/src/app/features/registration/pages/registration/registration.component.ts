import { Component } from '@angular/core';
import { RegistrationFormField, RegistrationFormService } from '../../service/registration-form.service';
import { RegistrationService } from '../../service/registration.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent {

  public readonly RegistrationFormField = RegistrationFormField;

  constructor(private registrationForm : RegistrationFormService, private registrationService : RegistrationService) {}

  get formGroup(){
    return this.registrationForm.formGroup;
  }

  get errorResolver(){
    return this.registrationForm.fieldErrorResolver;
  }


  doRegistration(){
    console.log(this.registrationForm.getUserRegistrationData())
  }

}
