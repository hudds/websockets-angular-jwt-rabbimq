import { MatSnackBar } from "@angular/material/snack-bar";
import { FormService } from "./form-service";
import { APIError, isAPIError } from "../types/api-error";

export class AppErrorHandler {


    constructor(private _snackBar : MatSnackBar, private formService : FormService){  }
    handle(error:any){
        if(isAPIError(error)){
            this.handleAPIError(error)
            return;
        }

        if(isAPIError(error.error)){
            this.handleAPIError(error.error);
            return;
        }
        this._snackBar.open("Ocorreu um erro desconhecido", "Ok", {})
        console.error(error)
    }

    private handleAPIError(error : APIError){
        this.formService.populateAPIFieldErrors(error);
        console.error(error)
        if(error.message){
            this._snackBar.open(error.message, "Ok", {})
            return;
        }
        if("VALIDATION_ERROR" !== error.type){
            this._snackBar.open("Ocorreu um erro desconhecido", "Ok", {})
            console.error(error)
        } 
    }

}