import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormGroupFieldErrorResolver } from 'src/app/common/lib/field-error-resolver';
import { AppValidators } from 'src/app/common/lib/validations';
import { UserRegistrationData } from '../types/user-registration-data';

@Injectable({
  providedIn: 'root'
})
export class RegistrationFormService {

  private _formGroup : FormGroup = new FormGroup({});

  private _fieldErrorResolver : FormGroupFieldErrorResolver = new FormGroupFieldErrorResolver(this._formGroup);

  constructor(formBuilder : FormBuilder) {
    this._formGroup = formBuilder.group({
      [RegistrationFormField.Name]: ["", [Validators.required]],
      [RegistrationFormField.Cpf]: ["", [Validators.required, AppValidators.cpf()]],
      [RegistrationFormField.BirthDate]: [null],
      [RegistrationFormField.Email]: [null, Validators.email],
      [RegistrationFormField.Password]: [null, [Validators.minLength(8), Validators.required]],
    });
    this._fieldErrorResolver = new FormGroupFieldErrorResolver(this._formGroup);
  }

  get formGroup() {
    return this._formGroup;
  }

  get fieldErrorResolver() {
    return this._fieldErrorResolver;
  }

  getUserRegistrationData() : UserRegistrationData {
    let name = this.formGroup.get(RegistrationFormField.Name)?.value;
    let cpf = this.formGroup.get(RegistrationFormField.Cpf)?.value;
    let birthDate = this.formGroup.get(RegistrationFormField.BirthDate)?.value;
    let email = this.formGroup.get(RegistrationFormField.Email)?.value;
    let password = this.formGroup.get(RegistrationFormField.Password)?.value;

    if(birthDate && birthDate.toISOString){
      birthDate = birthDate.toISOString().split('T')[0]
    }

    return {
      name,
      cpf,
      birthDate,
      email,
      password
    }
  }
}


export enum RegistrationFormField {
  Name="name",   
  Cpf="cpf",
  BirthDate="birthDate",
  Email="email",
  Password="password",
}