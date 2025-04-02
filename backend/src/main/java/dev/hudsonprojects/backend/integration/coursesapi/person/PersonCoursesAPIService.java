package dev.hudsonprojects.backend.integration.coursesapi.person;

import dev.hudsonprojects.backend.appuser.integration.coursesapi.PersonDTO;
import dev.hudsonprojects.backend.common.exception.InternalErrorException;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonCoursesAPIService {

    private final PersonCoursesAPIClient personApiClient;

    @Autowired
    public PersonCoursesAPIService(PersonCoursesAPIClient personApiClient) {
        this.personApiClient = personApiClient;
    }

    public Optional<PersonDTO> findByCpf(String cpf) {
        try {
            return personApiClient.getByCpf(cpf);
        } catch (CoursesAPIHttpException e) {
            throw new InternalErrorException("Failed to find person by CPF", e);
        }
    }
}
