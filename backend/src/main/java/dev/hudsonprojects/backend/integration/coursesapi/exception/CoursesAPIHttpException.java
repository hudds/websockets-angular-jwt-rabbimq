package dev.hudsonprojects.backend.integration.coursesapi.exception;

import dev.hudsonprojects.backend.integration.exception.HttpIntegrationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;

public class CoursesAPIHttpException extends HttpIntegrationException {
    public CoursesAPIHttpException() {
    }

    public CoursesAPIHttpException(ClientHttpResponse response) {
        super(response);
    }

    public CoursesAPIHttpException(ResponseEntity<?> response) {
        super(response);
    }

    public CoursesAPIHttpException(String message) {
        super(message);
    }

    public CoursesAPIHttpException(String message, ClientHttpResponse clientHttpResponse) {
        super(message, clientHttpResponse);
    }

    public CoursesAPIHttpException(String message, ResponseEntity<?> clientHttpResponse) {
        super(message, clientHttpResponse);
    }

    public CoursesAPIHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoursesAPIHttpException(String message, Throwable cause, ClientHttpResponse clientHttpResponse) {
        super(message, cause, clientHttpResponse);
    }

    public CoursesAPIHttpException(String message, Throwable cause, ResponseEntity<?> clientHttpResponse) {
        super(message, cause, clientHttpResponse);
    }

    public CoursesAPIHttpException(Throwable cause) {
        super(cause);
    }

    public CoursesAPIHttpException(HttpIntegrationException cause) {
        super(cause);
    }

    public CoursesAPIHttpException(String message, HttpIntegrationException cause) {
        super(message, cause);
    }

    public CoursesAPIHttpException(Throwable cause, ClientHttpResponse clientHttpResponse) {
        super(cause, clientHttpResponse);
    }

    public CoursesAPIHttpException(Throwable cause, ResponseEntity<?> clientHttpResponse) {
        super(cause, clientHttpResponse);
    }
}
