import { FormGroup } from "@angular/forms";
import { APIError } from "../types/api-error";

export interface FormService {
    formGroup : FormGroup
    populateAPIFieldErrors(apiError : APIError) : void;
}