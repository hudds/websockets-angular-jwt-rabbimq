import { Injectable } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { FormGroupFieldErrorResolver } from 'src/app/common/lib/field-error-resolver';
import { FormService } from 'src/app/common/lib/form-service';
import { APIError } from 'src/app/common/types/api-error';
import { UserCredentials } from '../types/user-credentials';

@Injectable({
  providedIn: 'root'
})
export class LoginFormService implements FormService {

  private _formGroup : FormGroup = new FormGroup({})
  private _errorResolver = new FormGroupFieldErrorResolver(this._formGroup)

  constructor(formBuilder : FormBuilder) { 
    this._formGroup = formBuilder.group({
      [LoginFormField.CpfOrEmail]: ["", [Validators.required]],
      [LoginFormField.Password]: ["", [Validators.required]]
    });
    this._errorResolver = new FormGroupFieldErrorResolver(this._formGroup)
  }
  
  get formGroup(){
    return this._formGroup;
  }

  get errorResolver(){
    return this._errorResolver
  }

  populateAPIFieldErrors(apiError: APIError): void {
    apiError.fieldErrors?.forEach(fieldError => {
      this.formGroup.get(fieldError.field)?.setErrors(fieldError)
    })
  }

  getUserCredentials(): UserCredentials | null{
    if(!this.formGroup.valid){
      return null
    }
    return {
      cpfOrEmail: this.formGroup.get(LoginFormField.CpfOrEmail)?.value,
      password: this.formGroup.get(LoginFormField.Password)?.value
    }
  }
}


export enum LoginFormField {
  CpfOrEmail="cpfOrEmail",   
  Password="password"
}