package dev.hudsonprojects.backend.common.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.hudsonprojects.backend.common.exception.APIErrorType;
import dev.hudsonprojects.backend.common.exception.APIMessageException;
import dev.hudsonprojects.backend.common.exception.NotFoundException;
import dev.hudsonprojects.backend.common.exception.UnauthorizedException;
import dev.hudsonprojects.backend.common.exception.ValidationException;
import dev.hudsonprojects.backend.common.messages.MessageResolver;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.backend.security.configuration.SecurityConfiguration;



@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private MessageResolver messageResolver;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorDetailsResolved> handleValidationException(ValidationException e){
		ErrorDetails errorDetails = e.getErrorDetails().orElse(ErrorDetailsBuilder.builder().setType(e.getType()).setMessage(e.getMessage()).build());
		if(errorDetails.getStatus() == null) {
			errorDetails.setStatus(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}
	
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDetailsResolved> handleNotFound(NotFoundException e){
		
		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setType(e.getType())
				.setStatus(HttpStatus.NOT_FOUND)
				.setMessage(e.getMessage())
				.build();
		
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorDetailsResolved> handleUnauthorized(UnauthorizedException e){
		
		ErrorDetails errorDetails = e.getErrorDetails()
				.orElse(ErrorDetailsBuilder.builder()
						.setType(e.getType())
						.setMessage(e.getMessage()).build());
		
		if(errorDetails.getStatus() == null) {
			errorDetails.setStatus(HttpStatus.UNAUTHORIZED);
		}
		
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetailsResolved> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

		
		ErrorDetails errorDetails = ErrorDetailsBuilder.withFieldErrors(e.getFieldErrors())
				.setStatus(HttpStatus.BAD_REQUEST)
				.setType(APIErrorType.VALIDATION_ERROR)
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(messageResolver.getResolved(errorDetails));
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorDetailsResolved> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setStatus(HttpStatus.METHOD_NOT_ALLOWED)
				.setMessage("error.http.methodNotAllowed")
				.setType(APIErrorType.HTTP_REQUEST_ERROR)
				.build();
		
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(messageResolver.getResolved(errorDetails));
	}
	
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ErrorDetailsResolved> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.setMessage("error.http.unsuportedMediaType")
				.setType(APIErrorType.HTTP_REQUEST_ERROR)
				.build();
		
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.body(messageResolver.getResolved(errorDetails));
	}
	
	@ExceptionHandler(APIMessageException.class)
	public ResponseEntity<ErrorDetailsResolved> handleAPIMessageException(APIMessageException e){
		
		ErrorDetails errorDetails = e.getErrorDetails()
				.orElse(ErrorDetailsBuilder.builder()
						.setType(e.getType())
						.setMessage(e.getMessage()).build());
		
		if(errorDetails.getStatus() == null) {
			errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsResolved> handleMethodArgumentNotValidException(Exception e){
		LOGGER.error("INTERNAL SERVER ERROR", e);
		
		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.setMessage("error.internalServerError")
				.setType(APIErrorType.INTERNAL_ERROR)
				.build();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(messageResolver.getResolved(errorDetails));
	}
	
	

}



