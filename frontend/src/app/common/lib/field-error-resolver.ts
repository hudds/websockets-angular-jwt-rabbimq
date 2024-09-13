import { AbstractControl, FormGroup, ValidationErrors } from "@angular/forms";
import { ValidationErrorCode } from "./validations";

const errorMessages = new Map<string, string>([
    [ValidationErrorCode.Required,'Campo obrigatório'],
    [ValidationErrorCode.Email,'E-mail inválido'],
    [ValidationErrorCode.Cpf,'CPF inválido'],
    [ValidationErrorCode.Phone,'Telefone inválido'],
    [ValidationErrorCode.MinLength,'Campo deve ter no mínimo p:{requiredLength} caracteres'],
])

export class FormGroupFieldErrorResolver {
    constructor(private formGroup?: FormGroup | null ){}
    
    public getErrorMessages(fieldName: string) : string[]{
        const errors = this.formGroup?.get(fieldName)?.errors
        return resolveErrors(errors);
    }
}


export class FormFieldErrorResolver {
    constructor(private control?: AbstractControl | null ){}
    public getErrorMessages() : string[]{
        const errors = this.control?.errors
        return resolveErrors(errors);
    }
}

function resolveErrors(errors? : ValidationErrors | null) : string[]{
    if(errors != null){
        const errorsKeys = Object.keys(errors);
        let messages = new Set<string>();
        for(let i = 0; i < errorsKeys.length; i++){
            let errorKey = errorsKeys[i]
            let error = errors[errorKey]
            if(error?.message != null){
                messages.add(applyParameterValues(error.message, error))
            } else {
                messages.add(resolveErrorMessageByKey(errorKey, error))
            }
        }
        return Array.from(messages);
    }

    return [];
}


function resolveErrorMessageByKey(errorKey : string, error? : any) : string{
    let errorMessage : any =  errorMessages.get(errorKey)
    if(errorMessage) {
          return applyParameterValues(errorMessage, error);
    }
    return 'Valor inválido';
}

function applyParameterValues(errorMessage:string, error : any){
    const parameterPattern = /.*p:\{.+\}.*/;
    if(parameterPattern.test(errorMessage) && error != null){
        let errorFields : string[] = Object.keys(error);
        for(let i = 0; i < errorFields.length; i++){
            let errorField = errorFields[i]
            let errorValue = error[errorField]
            const expression = `p:{${errorField}}`
            errorMessage = errorMessage.replace(expression, errorValue);
        }
    }
  return errorMessage;
}
