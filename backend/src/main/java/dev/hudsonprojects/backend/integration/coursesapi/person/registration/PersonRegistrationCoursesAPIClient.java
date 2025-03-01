package dev.hudsonprojects.backend.integration.coursesapi.person.registration;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.hudsonprojects.backend.appuser.integration.coursesapi.PersonDTO;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIAuthorizedHttpClient;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIHttpRequest;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.exception.CoursesAPIPersonRegistrationException;
import dev.hudsonprojects.backend.integration.coursesapi.security.exception.CoursesAPIAuthorizationException;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class PersonRegistrationCoursesAPIClient {

    private final CoursesAPIAuthorizedHttpClient apiClient;

    @Autowired
    public PersonRegistrationCoursesAPIClient(CoursesAPIAuthorizedHttpClient apiClient) {
        this.apiClient = apiClient;
    }


    public PersonDTO createByCpf(PersonRegistrationDTO personRegistrationDTO, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIPersonRegistrationException, CoursesAPIAuthorizationException {
        CoursesAPIHttpRequest httpRequest = CoursesAPIHttpRequest.builder()
                .setPath("person/registration/cpf/{cpf}")
                .namedURIParameter("{cpf}", personRegistrationDTO.getCpf())
                .setBody(personRegistrationDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .build();
        try {
            return apiClient.doPost(PersonDTO.class, httpRequest, integrationHttpProtocol);
        } catch (CoursesAPIHttpException e){
            throw new CoursesAPIPersonRegistrationException("Failed to create by CPF", e);
        }
    }

}
