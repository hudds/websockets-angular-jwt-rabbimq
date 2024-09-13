import { AbstractControl, FormGroup, ValidationErrors, Validators } from "@angular/forms";



const telefoneDDDPattern = /^\d{2}\s?9?\d{4}-?\d{4}$/;


export enum ValidationErrorCode {
    Cpf = 'cpf',
    Pattern = 'pattern',
    Phone = 'phone',
    FieldEquals = 'fieldEquals',
    RequireTrue = 'requireTrue',
    Required = 'required',
    Email = 'email',
    MinLength = 'minlength'
}
export class AppValidators {

    static cpf(message?: string): (constrol: AbstractControl) => ValidationErrors | null {
        return (control: AbstractControl) => {
            const value = control.value;
            if (null != value && !isCpfValid(value)) {
                return { [ValidationErrorCode.Cpf]: message ? { message } : value }
            }
            return null;
        }
    }

    static pattern(pattern: RegExp, message?: string): (constrol: AbstractControl) => ValidationErrors | null {
        return (control: AbstractControl) => {
            const value = control.value;
            if (null != value && pattern.test(value)) {
                return { [ValidationErrorCode.Pattern]: message ? { message } : value }
            }
            return null;
        }
    }

    static phoneDDD(message?: string): (constrol: AbstractControl) => ValidationErrors | null {
        return (control: AbstractControl) => {
            const value = control.value;
            if (null != value && !isTelefoneDDD(value)) {
                return { [ValidationErrorCode.Phone]: message ? { message } : value }
            }
            return null;
        }
    }

    static equalTo(targetField: string, message?: string): (constrol: AbstractControl) => ValidationErrors | null {
        return (control: AbstractControl) => {
            const targetValue = control.root.get(targetField)?.value
            if (control.value !== targetValue) {
                return { [ValidationErrorCode.FieldEquals]: { message, targetValue, controlValue: control.value } }
            }
            return null;
        }
    }

    static checkEquals(fields: string[], targets: string[], message?: string): (constrol: AbstractControl) => ValidationErrors | null {
        return (control: AbstractControl) => {
            const controls: AbstractControl[] = fields
                .map(fieldName => control.get(fieldName))
                .filter(control => control != null)
                .map(control => control as AbstractControl)

            const targetControls: AbstractControl[] = targets
                .map(fieldName => control.get(fieldName))
                .filter(control => control != null)
                .map(control => control as AbstractControl)
            if (controls.length == 0) {
                return null;
            }
            const values = controls.map(control => control.value)
            const firstValue = values[0]
            if (values.filter(value => value !== firstValue).length > 0) {
                const error = { [ValidationErrorCode.FieldEquals]: { message } }
                targetControls.forEach(control => {
                    if (control.errors) {
                        control.errors[ValidationErrorCode.FieldEquals] = { message }
                    } else {
                        control.setErrors(error)
                    }
                })
                return error
            }
            return null;
        }
    }

    static requireTrue(message?: string): (constrol: AbstractControl) => ValidationErrors | null {
        return (control: AbstractControl) => {
            const value = control.value
            if ('boolean' !== typeof value || !value) {
                return { [ValidationErrorCode.RequireTrue]: { message } }
            }
            return null;
        }
    }
}

function isCpfValid(cpf: string) : boolean {
    cpf = cpf.replace(/\D/g, "")
    if(cpf.length != 11){
        return false;
    }
    let sum = 0;
    let remainder = 0;
    if (cpf === "00000000000") return false;
    for (let i = 1; i <= 9; i++) {
        sum = sum + parseInt(cpf.substring(i - 1, i)) * (11 - i)
    }
    remainder = (sum * 10) % 11;

    if ((remainder == 10) || (remainder == 11)) { 
        remainder = 0 
    }

    if (remainder != parseInt(cpf.substring(9, 10))) { 
        return false 
    }

    sum = 0;
    for (let i = 1; i <= 10; i++) { 
        sum = sum + parseInt(cpf.substring(i - 1, i)) * (12 - i) 
    }
    remainder = (sum * 10) % 11;

    if ((remainder == 10) || (remainder == 11)){ 
        remainder = 0
    }
    if (remainder != parseInt(cpf.substring(10, 11))) {
        return false
    }
    return true;
}

function isTelefoneDDD(telefone: string) {
    return telefoneDDDPattern.test(telefone);
}