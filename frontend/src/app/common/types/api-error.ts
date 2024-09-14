import { ObjectUtils } from "../lib/utils/object-utils"


export interface APIError {
    type: APIErrorType
    message?: string
    status: string
    fieldErrors?: APIFieldError[]
}

export interface APIFieldError {
    field: string
    messages: string[]     
}

export enum APIErrorType{
    HTTP_REQUEST_ERROR = 'HTTP_REQUEST_ERROR',
	
	/**
	 * Usuário não autorizado
	 */
	UNAUTHORIZED = 'UNAUTHORIZED',
	
	/**
	 * Usuário está autorizado mas não possui permissão
	 */
	FORBIDDEN = 'FORBIDDEN',
	
	/**
	 * Erros de validação relacionados a regra de negócio
	 */
	VALIDATION_ERROR = 'VALIDATION_ERROR',
	/**
	 * Erros internos da a API
	 */
	INTERNAL_ERROR = 'INTERNAL_ERROR',
	
	/**
	 * Erros que não se encaixam em nenhuma das outras categorias 
	 */
	OTHER = 'OTHER',
	
	NOT_FOUND = 'NOT_FOUND'
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