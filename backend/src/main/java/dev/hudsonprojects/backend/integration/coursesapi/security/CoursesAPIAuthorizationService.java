package dev.hudsonprojects.backend.integration.coursesapi.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.integration.coursesapi.CoursesAPIParameters;
import dev.hudsonprojects.backend.integration.coursesapi.security.exception.CoursesAPIAuthorizationException;
import dev.hudsonprojects.backend.integration.exception.HttpIntegrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class CoursesAPIAuthorizationService {

    public static final String FAILED_TO_AUTHENTICATE_ON_COURSES_API = "Failed to authenticate on courses-api.";
    public static final String FAILED_TO_REFRESH_TOKEN_ON_COURSES_API = "Failed to refresh token on courses-api.";
    @Value("${integration.courses_api.credentials.username}")
    private String username;
    @Value("${integration.courses_api.credentials.password}")
    private String password;

    private final CoursesAPIParameters coursesAPIParameters;
    private final ObjectMapper objectMapper;

    private String accessToken;
    private String refreshToken;

    @Autowired
    public CoursesAPIAuthorizationService(CoursesAPIParameters coursesAPIParameters, ObjectMapper objectMapper) {
        this.coursesAPIParameters = coursesAPIParameters;
        this.objectMapper = objectMapper;
    }


    public synchronized String getAuthorizationHeaderValue() throws CoursesAPIAuthorizationException {
        return "Bearer " + getAccessToken();
    }

    public synchronized String getAccessToken() throws CoursesAPIAuthorizationException {
        if(requireNewAccessToken()) {
            refreshToken();
        }
        return accessToken;
    }

    private void refreshToken() throws CoursesAPIAuthorizationException {
        if(refreshToken == null){
            authenticate();
            return;
        }
        AtomicBoolean authenticationRenewed = new AtomicBoolean(false);
        AtomicReference<CoursesAPIAuthorizationException> exception = new AtomicReference<>(null);
        ResponseEntity<String> response = RestClient.builder()
                .build()
                .get()
                .uri(coursesAPIParameters.getApiUrlBuilder().path("user/auth/refresh-token").build())
                .header("refresh-token", refreshToken)
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.UNAUTHORIZED.value(), (request, resp) -> {
                    try {
                        authenticate();
                        authenticationRenewed.set(true);
                    } catch (CoursesAPIAuthorizationException e) {
                        exception.set(e);
                    }
                })
                .onStatus(status -> status.value() != HttpStatus.UNAUTHORIZED.value() && !status.is2xxSuccessful(), (request, resp) -> {
                    exception.set(new CoursesAPIAuthorizationException(FAILED_TO_REFRESH_TOKEN_ON_COURSES_API, resp));
                }).toEntity(String.class);

        if(exception.get() != null){
            throw exception.get();
        }
        if(authenticationRenewed.get()){
            return;
        }
        String responseBody = response.getBody();
        if(responseBody == null) {
            throw new CoursesAPIAuthorizationException(FAILED_TO_REFRESH_TOKEN_ON_COURSES_API + " No response body.", response);
        }
        try {
            CoursesAPIAuthenticationResponse authenticationResponse = objectMapper.readValue(responseBody, CoursesAPIAuthenticationResponse.class);
            this.accessToken = authenticationResponse.getAccessToken();
            this.refreshToken = authenticationResponse.getRefreshToken();
        } catch (JsonProcessingException e) {
            throw new CoursesAPIAuthorizationException(FAILED_TO_REFRESH_TOKEN_ON_COURSES_API + " Unexpected response body." , e, response);
        }
    }

    private void authenticate() throws CoursesAPIAuthorizationException {
        CoursesAPICredentials credentials = new CoursesAPICredentials(username, password);
        AtomicReference<CoursesAPIAuthorizationException> exception = new AtomicReference<>(null);
        ResponseEntity<CoursesAPIAuthenticationResponse> response = RestClient.builder()
                .build()
                .post()
                .uri(coursesAPIParameters.getApiUrlBuilder().path("user/auth").build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(credentials)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), (request, resp) -> {
                    exception.set(new CoursesAPIAuthorizationException(FAILED_TO_AUTHENTICATE_ON_COURSES_API + " Response status: " + resp.getStatusCode().value()));
                }).toEntity(CoursesAPIAuthenticationResponse.class);

        if(exception.get() != null){
            throw exception.get();
        }
        CoursesAPIAuthenticationResponse responseBody = response.getBody();
        if(responseBody == null) {
            throw new CoursesAPIAuthorizationException(FAILED_TO_AUTHENTICATE_ON_COURSES_API + " No response body. Status code: " + response.getStatusCode().value());
        }
        this.accessToken = responseBody.getAccessToken();
        this.refreshToken = responseBody.getRefreshToken();
    }


    private boolean requireNewAccessToken() throws CoursesAPIAuthorizationException {
        return accessToken == null || isTokenExpired();
    }

    private boolean isTokenExpired() throws CoursesAPIAuthorizationException {
        String decoded = new String(Base64.getDecoder().decode(accessToken.split("\\.")[1]));
        try {
            long epochSecond = objectMapper.reader().readTree(decoded).get("exp").asLong() - 120;
            return Instant.now().getEpochSecond() > epochSecond;
        } catch (JsonProcessingException e) {
            throw new CoursesAPIAuthorizationException("fail to decode access token", e);
        }
    }


}
