import { ObjectUtils } from "../lib/utils/object-utils"


export interface APIError {
    type: string
    message?: string
    status: string
    fieldErrors?: APIFieldError[]
}

export interface APIFieldError {
    field: string
    messages: string[]     
}


export function isAPIError(object : any) : object is APIError {
    if(object == null){
        return false;
    }

    if (ObjectUtils.isPrimitive(object)) {
        return false;
    }

    if(Array.isArray(object)){
        return false;
    }

    return ObjectUtils.isString(object.type)
           && (object.message == null || ObjectUtils.isString(object.message))
           && ObjectUtils.isString(object.status)
           && (object.fieldErrors == null || (Array.isArray(object.fieldErrors) 
           && object.fieldErrors.every(isAPIFieldError)))

}


export function isAPIFieldError(object : any) : object is APIFieldError {
    if(object == null){
        return false;
    }

    if (ObjectUtils.isPrimitive(object)) {
        return false;
    }

    if(Array.isArray(object)){
        return false;
    }

    return ObjectUtils.isString(object.field)
           && ObjectUtils.isStringArray(object.messages)


}