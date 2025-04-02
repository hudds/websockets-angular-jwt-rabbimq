package dev.hudsonprojects.backend.integration.coursesapi.person;

import dev.hudsonprojects.backend.appuser.integration.coursesapi.PersonDTO;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIAuthorizedHttpClient;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIHttpRequest;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.PersonRegistrationDTO;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.exception.CoursesAPIPersonRegistrationException;
import dev.hudsonprojects.backend.integration.coursesapi.security.exception.CoursesAPIAuthorizationException;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonCoursesAPIClient {

    private final CoursesAPIAuthorizedHttpClient apiClient;

    @Autowired
    public PersonCoursesAPIClient(CoursesAPIAuthorizedHttpClient apiClient) {
        this.apiClient = apiClient;
    }


    public Optional<PersonDTO> getByCpf(String cpf) throws CoursesAPIHttpException {
        CoursesAPIHttpRequest httpRequest = CoursesAPIHttpRequest.builder()
                .setPath("person/cpf/{cpf}")
                .namedURIParameter("{cpf}", StringUtils.removeNonDigits(cpf))
                .build();
        try {
            return Optional.of(apiClient.doGet(PersonDTO.class, httpRequest));
        } catch (CoursesAPIHttpException e){
            if(e.getStatusCode() == 404){
                return Optional.empty();
            }
            throw new CoursesAPIHttpException("Failed to find person by CPF", e);
        }
    }

}
