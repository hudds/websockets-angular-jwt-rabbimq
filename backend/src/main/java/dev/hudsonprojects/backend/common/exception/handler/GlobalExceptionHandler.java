package dev.hudsonprojects.backend.common.exception.handler;

import dev.hudsonprojects.backend.common.exception.*;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.hudsonprojects.backend.common.messages.MessageResolver;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsResolved;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


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
		logException(e);
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
		logException(e);
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
		logException(e);
		return ResponseEntity.status(errorDetails.getStatus())
				.header("WWW-Authenticate", "Bearer")
				.body(messageResolver.getResolved(errorDetails));
	}

	@ExceptionHandler(InternalErrorException.class)
	public ResponseEntity<ErrorDetailsResolved> handleInternalErrorException(InternalErrorException e){
		LOGGER.error("INTERNAL SERVER ERROR", e);
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetailsResolved> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

		
		ErrorDetails errorDetails = ErrorDetailsBuilder.withFieldErrors(e.getFieldErrors())
				.setStatus(HttpStatus.BAD_REQUEST)
				.setType(APIErrorType.VALIDATION_ERROR)
				.build();
		logException(e);
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
		logException(e);
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
		logException(e);
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
		logException(e);
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetailsResolved> handleBadCredentialsException(BadCredentialsException e){
		
		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setStatus(HttpStatus.UNAUTHORIZED)
				.setMessage("authentication.badCredentials")
				.setType(APIErrorType.UNAUTHORIZED)
				.build();
		logException(e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(messageResolver.getResolved(errorDetails));
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsResolved> handleException(Exception e){
		LOGGER.error("INTERNAL SERVER ERROR", e);
		
		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.setMessage("error.internalServerError")
				.setType(APIErrorType.INTERNAL_ERROR)
				.build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(messageResolver.getResolved(errorDetails));
	}


	private static void logException(Exception e) {
		LOGGER.info("Exception handled {}", e.getClass().getName());
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorDetailsResolved> handleNoResourceFoundException(NoResourceFoundException e){

		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setType(APIErrorType.NOT_FOUND)
				.setStatus(HttpStatus.NOT_FOUND)
				.setMessage(e.getMessage())
				.build();
		logException(e);
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}




	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDetailsResolved> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){

		ErrorDetails errorDetails = ErrorDetailsBuilder.withMessage("validation.http.request.invalid")
				.setStatus(HttpStatus.BAD_REQUEST)
				.setType(APIErrorType.HTTP_REQUEST_ERROR)
				.build();
		logException(e);
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ErrorDetailsResolved> handleMethodArgumentNotValidException(HandlerMethodValidationException e){
		List<FieldError> list = e.getAllValidationResults()
				.stream()
				.map(ParameterValidationResult::getResolvableErrors)
				.flatMap(Collection::stream)
				.filter(Objects::nonNull)
				.filter(error -> FieldError.class.isAssignableFrom(error.getClass()))
				.map(FieldError.class::cast)
				.toList();

		List<String> messages = e.getAllValidationResults()
				.stream()
				.map(ParameterValidationResult::getResolvableErrors)
				.flatMap(Collection::stream)
				.filter(Objects::nonNull)
				.filter(error -> !FieldError.class.isAssignableFrom(error.getClass()))
				.map(MessageSourceResolvable::getDefaultMessage)
				.filter(StringUtils::isNotBlank)
				.toList();



		var errorDetailsBuilder = ErrorDetailsBuilder.withFieldErrors(list)
				.setStatus(HttpStatus.BAD_REQUEST)
				.setType(APIErrorType.VALIDATION_ERROR);

		if (!messages.isEmpty()){
			errorDetailsBuilder.addFieldError(null, messages);
		}

		ErrorDetails errorDetails = errorDetailsBuilder.build();
		logException(e);
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorDetailsResolved> handleExpiredJwtException(ExpiredJwtException e){

		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setStatus(HttpStatus.UNAUTHORIZED)
				.setMessage("authentication.expired")
				.setType(APIErrorType.UNAUTHORIZED)
				.build();
		logException(e);
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ErrorDetailsResolved> handleMalformedJwtException(MalformedJwtException e){

		ErrorDetails errorDetails = ErrorDetailsBuilder.builder()
				.setStatus(HttpStatus.UNAUTHORIZED)
				.setMessage("authentication.jwt.malformed")
				.setType(APIErrorType.UNAUTHORIZED)
				.build();
		logException(e);
		return ResponseEntity.status(errorDetails.getStatus())
				.body(messageResolver.getResolved(errorDetails));
	}

}



