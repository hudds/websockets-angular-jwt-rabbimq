package dev.hudsonprojects.backend.integration.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpIntegrationException extends IntegrationException {

    private Integer statusCode;
    private String responseBody;
    private final Map<String, String> responseHeaders = new LinkedHashMap<>();

    public HttpIntegrationException() {
    }

    public HttpIntegrationException(ClientHttpResponse response) {
        fillResponse(response);
    }

    public HttpIntegrationException(ResponseEntity<?> response) {
        fillResponse(response);
    }

    private void fillResponse(ClientHttpResponse response) {
        if(response != null){
            try {
                HttpStatusCode responseStatus = response.getStatusCode();
                statusCode = responseStatus != null ? responseStatus.value() : null;
            } catch (Exception e) {
                // ignored
            }
            try {
                responseBody = new String(response.getBody().readAllBytes());
            } catch (Exception e) {
                // ignored
            }
            try {
                response.getHeaders().forEach((header, values) -> responseHeaders.put(header, String.join(", ", values)));
            } catch (Exception e) {
                // ignored
            }

        }
    }

    private void fillResponse(ResponseEntity<?> response) {
        if(response != null){
            try {
                HttpStatusCode responseStatus = response.getStatusCode();
                statusCode = responseStatus != null ? responseStatus.value() : null;
            } catch (Exception e) {
                // ignored
            }
            try {
                Object entity = response.getBody();
                if(entity instanceof CharSequence){
                    responseBody = String.valueOf(entity);
                } else if (entity != null && isJson(response.getHeaders())) {
                    try {
                        responseBody = new ObjectMapper().writer().writeValueAsString(entity);
                    } catch (Exception e) {
                        responseBody = String.valueOf(entity);
                    }
                } else if (entity != null) {
                    responseBody = String.valueOf(entity);
                }
            } catch (Exception e) {
                // ignored
            }
            try {
                response.getHeaders()
                        .forEach((header, values) -> responseHeaders.put(header, String.join(", ", values)));
            } catch (Exception e) {
                // ignored
            }
        }
    }

    private boolean isJson(HttpHeaders headers) {
        if(headers == null){
            return false;
        }
        return MediaType.APPLICATION_JSON.equals(headers.getContentType()) ||
                        MediaType.APPLICATION_JSON_UTF8.equals(headers.getContentType());
    }

    public HttpIntegrationException(String message) {
        super(message);
    }

    public HttpIntegrationException(String message, ClientHttpResponse clientHttpResponse) {
        super(message);
        fillResponse(clientHttpResponse);
    }

    public HttpIntegrationException(String message, ResponseEntity<?> clientHttpResponse) {
        super(message);
        fillResponse(clientHttpResponse);
    }

    public HttpIntegrationException(String message, Throwable cause) {
        super(message, cause);
        if(getClass().isAssignableFrom(cause.getClass())){
            fillValues(getClass().cast(cause));
        }
    }

    public HttpIntegrationException(String message, Throwable cause, ClientHttpResponse clientHttpResponse) {
        super(message, cause);
        fillResponse(clientHttpResponse);
    }

    public HttpIntegrationException(String message, Throwable cause, ResponseEntity<?> clientHttpResponse) {
        super(message, cause);
        fillResponse(clientHttpResponse);
    }

    public HttpIntegrationException(Throwable cause) {
        super(cause);
    }

    public HttpIntegrationException(HttpIntegrationException cause) {
        super(cause);
        fillValues(cause);
    }

    public HttpIntegrationException(String message, HttpIntegrationException cause) {
        super(message, cause);
        fillValues(cause);
    }

    private void fillValues(HttpIntegrationException cause) {
        this.statusCode = cause.statusCode;
        this.responseBody = cause.responseBody;
        this.responseHeaders.putAll(cause.responseHeaders);
    }

    public HttpIntegrationException(Throwable cause, ClientHttpResponse clientHttpResponse) {
        super(cause);
        fillResponse(clientHttpResponse);
    }

    public HttpIntegrationException(Throwable cause, ResponseEntity<?> clientHttpResponse) {
        super(cause);
        fillResponse(clientHttpResponse);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Map<String, String> getResponseHeaders() {
        return new LinkedHashMap<>(responseHeaders);
    }

    @Override
    public String getMessage() {
        if(statusCode == null) {
            return super.getMessage();
        }
        return super.getMessage() + "  Status code: " + statusCode;
    }
}
