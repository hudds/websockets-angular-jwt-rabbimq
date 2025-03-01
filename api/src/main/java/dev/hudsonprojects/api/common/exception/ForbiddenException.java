package dev.hudsonprojects.api.common.exception;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetails;

public class ForbiddenException extends APIMessageException {

	private static final long serialVersionUID = 1L;

	public ForbiddenException(ErrorDetails errorDetails) {
		super(errorDetails, APIErrorType.FORBIDDEN);
	}

	public ForbiddenException(ErrorDetails errorDetails, String message) {
		super(errorDetails, APIErrorType.FORBIDDEN, message);
	}

	public ForbiddenException(ErrorDetails errorDetails, String message, Throwable cause) {
		super(errorDetails, APIErrorType.FORBIDDEN, message, cause);
	}

	public ForbiddenException(ErrorDetails errorDetails, Throwable cause) {
		super(errorDetails, APIErrorType.FORBIDDEN, cause);
	}

}
