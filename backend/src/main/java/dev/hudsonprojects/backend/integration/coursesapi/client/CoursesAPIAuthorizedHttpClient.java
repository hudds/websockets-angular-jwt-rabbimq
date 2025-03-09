package dev.hudsonprojects.backend.integration.coursesapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.integration.coursesapi.CoursesAPIParameters;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.security.CoursesAPIAuthorizationService;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocol;
import dev.hudsonprojects.backend.integration.protocol.IntegrationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Service
public class CoursesAPIAuthorizedHttpClient {

    private final CoursesAPIAuthorizationService coursesAPIAuthorizationService;
    private final CoursesAPIParameters coursesAPIParameters;
    private final ObjectMapper objectMapper;

    @Autowired
    public CoursesAPIAuthorizedHttpClient(CoursesAPIAuthorizationService coursesAPIAuthorizationService, CoursesAPIParameters coursesAPIParameters, ObjectMapper objectMapper) {
        this.coursesAPIAuthorizationService = coursesAPIAuthorizationService;
        this.coursesAPIParameters = coursesAPIParameters;
        this.objectMapper = objectMapper;
    }

    public void doPost(CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        doPost((Class<Object>) null, httpRequest, null);
    }

    public <T> T doPost(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doPost(expectedResponseType, httpRequest, null);
    }

    public <T> T doPost(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.POST, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public void doGet(CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        doGet((Class<Object>) null, httpRequest, null);
    }

    public <T> T doGet(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doGet(expectedResponseType, httpRequest, null);
    }

    public <T> T doGet(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.GET, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public void doPut(CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        doPut((Class<?>) null, httpRequest, null);
    }

    public <T> T doPut(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doPut(expectedResponseType, httpRequest, null);
    }

    public <T> T doPut(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.PUT, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public void doDelete(CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        doDelete((Class<Object>) null, httpRequest, null);
    }

    public <T> T doDelete(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doDelete(expectedResponseType, httpRequest, null);
    }

    public <T> T doDelete(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.DELETE, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public void doPatch(CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        doPatch((Class<Object>) null, httpRequest, null);
    }

    public <T> T doPatch(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doPatch(expectedResponseType, httpRequest, null);
    }

    public <T> T doPatch(TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.PATCH, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public <T> T doPost(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doPost(expectedResponseType, httpRequest, null);
    }

    public <T> T doPost(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.POST, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public <T> T doGet(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doGet(expectedResponseType, httpRequest, null);
    }

    public <T> T doGet(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.GET, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public <T> T doPut(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doPut(expectedResponseType, httpRequest, null);
    }

    public <T> T doPut(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.PUT, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public <T> T doDelete(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doDelete(expectedResponseType, httpRequest, null);
    }

    public <T> T doDelete(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.DELETE, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public <T> T doPatch(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest) throws CoursesAPIHttpException {
        return doPatch(expectedResponseType, httpRequest, null);
    }

    public <T> T doPatch(Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        return doRequest(HttpMethod.PATCH, expectedResponseType, httpRequest, integrationHttpProtocol);
    }

    public <T> T doRequest(HttpMethod httpMethod, TypeReference<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        String responseBody = doRequest(httpMethod, httpRequest, integrationHttpProtocol);

        if (expectedResponseType == null || responseBody == null) {
            return null;
        }

        try {
            return objectMapper.readValue(responseBody, expectedResponseType);
        } catch (JsonProcessingException e) {
            throw new CoursesAPIHttpException("Failed to deserialize response", e);
        }
    }

    public <T> T doRequest(HttpMethod httpMethod, Class<T> expectedResponseType, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        String responseBody = doRequest(httpMethod, httpRequest, integrationHttpProtocol);

        if (expectedResponseType == null || responseBody == null || (expectedResponseType == String.class && responseBody.isBlank())) {
            return null;
        }

        if(expectedResponseType == String.class){
            return (T) responseBody;
        }

        try {
            return objectMapper.readValue(responseBody, expectedResponseType);
        } catch (JsonProcessingException e) {
            throw new CoursesAPIHttpException("Failed to deserialize response", e);
        }
    }

    private String doRequest(HttpMethod httpMethod, CoursesAPIHttpRequest httpRequest, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        String url = coursesAPIParameters.buildUrl(httpRequest);
        String requestBody = null;
        if (httpRequest.getBody() instanceof String stringBody) {
            requestBody = stringBody;
        } else if (httpRequest.getBody() != null) {
            try {
                requestBody = objectMapper.writer().writeValueAsString(httpRequest.getBody());
            } catch (JsonProcessingException e) {
                integrationHttpProtocol.setIntegrationStatus(IntegrationStatus.ERROR);
                throw new CoursesAPIHttpException("Failed to serialize request body", e);
            }
        }
        if(integrationHttpProtocol != null) {
            integrationHttpProtocol.setContent(requestBody);
        }
        var exception = new Object() {
            CoursesAPIHttpException value = null;
        };
        RestClient restClient = RestClient.builder().build();

        RestClient.RequestBodySpec requestBodySpec = restClient.method(httpMethod).uri(url);
        requestBodySpec.header("Authorization", coursesAPIAuthorizationService.getAuthorizationHeaderValue());
        httpRequest.getHeaders().forEach((name, value) -> {
            if(!name.equalsIgnoreCase("authorization")){
                requestBodySpec.header(name, String.valueOf(value));
            }
        });
        if (requestBody != null) {
            requestBodySpec.body(requestBody);
        }

        if(integrationHttpProtocol != null) {
            integrationHttpProtocol.setSentAt(LocalDateTime.now());
        }
        ResponseEntity<String> response = requestBodySpec.retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), (request, errorResponse) -> {
                    exception.value = new CoursesAPIHttpException("Unexpected response status", errorResponse);
                })
                .toEntity(String.class);

        if(exception.value != null){
            throw exception.value;
        }

        String responseBody = response.getBody();
        if(integrationHttpProtocol != null) {
            integrationHttpProtocol.setResponseStatus(response.getStatusCode().value());
            integrationHttpProtocol.setResponse(responseBody);
            integrationHttpProtocol.setResponseReceivedAt(LocalDateTime.now());
        }
        return responseBody;
    }
}
