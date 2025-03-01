package dev.hudsonprojects.api.common.exception;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetails;

public class APIInternalErrorException extends APIMessageException{

    public APIInternalErrorException() {
        super(null, APIErrorType.INTERNAL_ERROR);
    }

    public APIInternalErrorException(Throwable cause) {
        super(null, APIErrorType.INTERNAL_ERROR, cause);
    }

    public APIInternalErrorException(ErrorDetails rrrorDetails) {
        super(rrrorDetails, APIErrorType.INTERNAL_ERROR);
    }

    public APIInternalErrorException(ErrorDetails rrrorDetails, String message) {
        super(rrrorDetails, APIErrorType.INTERNAL_ERROR, message);
    }

    public APIInternalErrorException(ErrorDetails rrrorDetails, String message, Throwable cause) {
        super(rrrorDetails, APIErrorType.INTERNAL_ERROR, message, cause);
    }

    public APIInternalErrorException(ErrorDetails rrrorDetails, Throwable cause) {
        super(rrrorDetails, APIErrorType.INTERNAL_ERROR, cause);
    }
}
