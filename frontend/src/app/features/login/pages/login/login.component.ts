import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ObjectUtils } from 'src/app/common/lib/utils/object-utils';
import { FlashMessageService } from 'src/app/common/service/flash-message.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  registrationMessage = ""

  constructor(private flashMessages : FlashMessageService, private _snackBar : MatSnackBar){}


  ngOnInit(): void {
    let message = this.flashMessages.getMessage("registrationMessage")
    if(ObjectUtils.isString(message)){
      this._snackBar.open(message, "Ok")
    }
  }




}
