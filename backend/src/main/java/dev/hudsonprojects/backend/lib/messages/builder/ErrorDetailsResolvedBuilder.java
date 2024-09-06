package dev.hudsonprojects.backend.lib.messages.builder;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import dev.hudsonprojects.backend.exception.APIErrorType;
import dev.hudsonprojects.backend.lib.messages.APIFieldErrorResolved;
import dev.hudsonprojects.backend.lib.messages.ErrorDetailsResolved;


public class ErrorDetailsResolvedBuilder {
	
	
	private final ErrorDetailsResolved errorDetails;
	
	private ErrorDetailsResolvedBuilder() {
		errorDetails = new ErrorDetailsResolved();
	}
	
	
	public ErrorDetailsResolvedBuilder setType(APIErrorType type) {
		this.errorDetails.setType(type);
		return this;
	}
	
	public ErrorDetailsResolvedBuilder setMessage(String message) {
		this.errorDetails.setMessage(message);
		return this;
	}
	
	public ErrorDetailsResolvedBuilder setStatus(HttpStatus status) {
		this.errorDetails.setStatus(status);
		return this;
	}
	
	public ErrorDetailsResolvedBuilder setFieldErrors(List<APIFieldErrorResolved> fieldErrors) {
		this.errorDetails.setFieldErrors(fieldErrors);
		return this;
	}
	
	
	public ErrorDetailsResolved build() {
		return this.errorDetails;
	}
	
	public static ErrorDetailsResolvedBuilder builder() {
		return new ErrorDetailsResolvedBuilder();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(errorDetails);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorDetailsResolvedBuilder other = (ErrorDetailsResolvedBuilder) obj;
		return Objects.equals(errorDetails, other.errorDetails);
	}


	@Override
	public String toString() {
		return "ErrorDetailsResolvedBuilder [errorDetails=" + errorDetails + "]";
	}
	
}
