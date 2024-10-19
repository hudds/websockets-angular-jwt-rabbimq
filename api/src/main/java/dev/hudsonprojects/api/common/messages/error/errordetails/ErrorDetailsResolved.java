package dev.hudsonprojects.api.common.messages.error.errordetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import dev.hudsonprojects.api.common.exception.APIErrorType;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldErrorResolved;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDetailsResolved {

    private APIErrorType type;
    private String message;
    private HttpStatus status;
    private final List<APIFieldErrorResolved> fieldErrors = new ArrayList<>();

    public APIErrorType getType() {
        return type;
    }

    public void setType(APIErrorType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<APIFieldErrorResolved> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<APIFieldErrorResolved> fieldErrors) {
    	this.fieldErrors.clear();
    	if(fieldErrors != null) {
    		this.fieldErrors.addAll(fieldErrors);
    	}
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldErrors, message, status, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ErrorDetailsResolved other = (ErrorDetailsResolved) obj;
        return Objects.equals(fieldErrors, other.fieldErrors) && Objects.equals(message, other.message)
                && status == other.status && type == other.type;
    }

    @Override
    public String toString() {
        return "ErrorDetailsResolved [type=" + type + ", message=" + message + ", status=" + status + ", fieldErrors="
                + fieldErrors + "]";
    }

}
