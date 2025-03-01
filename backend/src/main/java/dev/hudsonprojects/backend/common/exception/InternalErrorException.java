package dev.hudsonprojects.backend.common.exception;

import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetails;

public class InternalErrorException extends APIMessageException{
    public InternalErrorException(ErrorDetails errorDetails) {
        super(errorDetails, APIErrorType.INTERNAL_ERROR);
    }

    public InternalErrorException(ErrorDetails errorDetails, Throwable cause) {
        super(errorDetails, APIErrorType.INTERNAL_ERROR, cause);
    }

    public InternalErrorException(ErrorDetails errorDetails, String exceptionMessage, Throwable cause) {
        super(errorDetails, APIErrorType.INTERNAL_ERROR, exceptionMessage, cause);
    }

    public InternalErrorException(ErrorDetails errorDetails, String exceptionMessage) {
        super(errorDetails, APIErrorType.INTERNAL_ERROR, exceptionMessage);
    }

    public InternalErrorException(String exceptionMessage, Throwable cause) {
        super(APIErrorType.INTERNAL_ERROR, exceptionMessage, cause);
    }

    public InternalErrorException(String exceptionMessage) {
        super(APIErrorType.INTERNAL_ERROR, exceptionMessage);
    }
}
