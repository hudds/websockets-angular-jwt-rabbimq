package dev.hudsonprojects.api.common.exception.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.messages.MessageResolver;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
	
	@Mock
	private MessageResolver messageResolver;
	
	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@Test
	void shouldFillStatusWithBadRequestIfNoneInformedForValidationException() {
		ResponseEntity<ErrorDetailsResolved> responseEntity = globalExceptionHandler.handleValidationException(new ValidationException(null));
		assertThat(responseEntity.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)).isTrue();
	}
	
	@Test
	void shouldFillStatusWithErrorDetailsForValidationException() {
		HttpStatus httpStatus = HttpStatus.OK;
		ErrorDetails errorDetails = ErrorDetailsBuilder.builder().setStatus(httpStatus).build();
		ResponseEntity<ErrorDetailsResolved> responseEntity = globalExceptionHandler.handleValidationException(new ValidationException(errorDetails));
		assertThat(responseEntity.getStatusCode().isSameCodeAs(httpStatus)).isTrue();
	}

}
