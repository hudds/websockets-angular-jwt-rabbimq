package dev.hudsonprojects.backend.integration.coursesapi.person.registration.exception;

import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.exception.HttpIntegrationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;

public class CoursesAPIPersonRegistrationException extends CoursesAPIHttpException {
    public CoursesAPIPersonRegistrationException() {
    }

    public CoursesAPIPersonRegistrationException(ClientHttpResponse response) {
        super(response);
    }

    public CoursesAPIPersonRegistrationException(ResponseEntity<?> response) {
        super(response);
    }

    public CoursesAPIPersonRegistrationException(String message) {
        super(message);
    }

    public CoursesAPIPersonRegistrationException(String message, ClientHttpResponse clientHttpResponse) {
        super(message, clientHttpResponse);
    }

    public CoursesAPIPersonRegistrationException(String message, ResponseEntity<?> clientHttpResponse) {
        super(message, clientHttpResponse);
    }

    public CoursesAPIPersonRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoursesAPIPersonRegistrationException(String message, Throwable cause, ClientHttpResponse clientHttpResponse) {
        super(message, cause, clientHttpResponse);
    }

    public CoursesAPIPersonRegistrationException(String message, Throwable cause, ResponseEntity<?> clientHttpResponse) {
        super(message, cause, clientHttpResponse);
    }

    public CoursesAPIPersonRegistrationException(Throwable cause) {
        super(cause);
    }

    public CoursesAPIPersonRegistrationException(HttpIntegrationException cause) {
        super(cause);
    }

    public CoursesAPIPersonRegistrationException(String message, HttpIntegrationException cause) {
        super(message, cause);
    }

    public CoursesAPIPersonRegistrationException(Throwable cause, ClientHttpResponse clientHttpResponse) {
        super(cause, clientHttpResponse);
    }

    public CoursesAPIPersonRegistrationException(Throwable cause, ResponseEntity<?> clientHttpResponse) {
        super(cause, clientHttpResponse);
    }
}
