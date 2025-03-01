package dev.hudsonprojects.backend.integration.coursesapi.security.exception;

import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;

public class CoursesAPIAuthorizationException extends CoursesAPIHttpException {
    public CoursesAPIAuthorizationException() {
    }

    public CoursesAPIAuthorizationException(ClientHttpResponse response) {
        super(response);
    }

    public CoursesAPIAuthorizationException(ResponseEntity<?> response) {
        super(response);
    }

    public CoursesAPIAuthorizationException(String message) {
        super(message);
    }

    public CoursesAPIAuthorizationException(String message, ClientHttpResponse clientHttpResponse) {
        super(message, clientHttpResponse);
    }

    public CoursesAPIAuthorizationException(String message, ResponseEntity<?> clientHttpResponse) {
        super(message, clientHttpResponse);
    }

    public CoursesAPIAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoursesAPIAuthorizationException(String message, Throwable cause, ClientHttpResponse clientHttpResponse) {
        super(message, cause, clientHttpResponse);
    }

    public CoursesAPIAuthorizationException(String message, Throwable cause, ResponseEntity<?> clientHttpResponse) {
        super(message, cause, clientHttpResponse);
    }

    public CoursesAPIAuthorizationException(Throwable cause) {
        super(cause);
    }

    public CoursesAPIAuthorizationException(Throwable cause, ClientHttpResponse clientHttpResponse) {
        super(cause, clientHttpResponse);
    }

    public CoursesAPIAuthorizationException(Throwable cause, ResponseEntity<?> clientHttpResponse) {
        super(cause, clientHttpResponse);
    }
}
