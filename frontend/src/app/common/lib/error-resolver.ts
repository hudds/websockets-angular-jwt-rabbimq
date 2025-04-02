import { MatSnackBar } from "@angular/material/snack-bar";
import { FormService } from "./form-service";
import { APIError, APIErrorType, isAPIError } from "../types/api-error";

export class AppErrorHandler {

    public readonly expectedApiErrors : APIErrorType[] = [APIErrorType.VALIDATION_ERROR];

    showFieldErrorAsMessage : boolean = false

    constructor(private _snackBar : MatSnackBar, private formService? : FormService){  }

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
        if(this.formService){
            this.formService.populateAPIFieldErrors(error);
        } else if(this.showFieldErrorAsMessage && error.fieldErrors){
            let messages = new Set<string>();
            error.fieldErrors
                .flatMap(fError => fError.messages)
                .forEach(message => messages.add(message));
            messages.forEach(message => this._snackBar.open(message, "Ok", {}))
        }
        if(this.expectedApiErrors.includes(error.type) && error.message){
            this._snackBar.open(error.message, "Ok", {})
            return;
        }
        
        this._snackBar.open("Ocorreu um erro desconhecido", "Ok", {})
        console.error(error)
    }

}