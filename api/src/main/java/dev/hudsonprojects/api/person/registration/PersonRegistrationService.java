package dev.hudsonprojects.api.person.registration;

import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.person.Person;
import dev.hudsonprojects.api.person.PersonDTO;
import dev.hudsonprojects.api.person.PersonRepository;
import dev.hudsonprojects.api.person.PersonValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonRegistrationService {

    private final PersonRepository personRepository;
    private final PersonValidationService personValidationService;

    @Autowired
    public PersonRegistrationService(PersonRepository personRepository, PersonValidationService personValidationService) {
        this.personRepository = personRepository;
        this.personValidationService = personValidationService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PersonDTO createByCpf(PersonRegistrationDTO personRegistration) {
        Person person = personRegistration.toPerson();
        Optional<Person> registeredPerson = Optional.ofNullable(person.getCpf())
                .filter(StringUtils::isNotBlank)
                .flatMap(personRepository::findPersonByCpf);
        if(registeredPerson.isPresent()) {
            return registeredPerson.map(PersonDTO::new).get();
        }
        List<APIFieldError> errors = personValidationService.validate(person);
        if(!errors.isEmpty()) {
            throw new ValidationException(ErrorDetailsBuilder.withAPIFieldErrors(errors).build());
        }
        personRepository.save(person);
        return new PersonDTO(person);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PersonDTO createOrUpdateByCpf(PersonRegistrationDTO personRegistration) {
        Person person = toPerson(personRegistration);
        List<APIFieldError> errors = personValidationService.validate(person);
        if(!errors.isEmpty()) {
            throw new ValidationException(ErrorDetailsBuilder.withAPIFieldErrors(errors).build());
        }
        personRepository.save(person);
        return new PersonDTO(person);
    }

    private Person toPerson(PersonRegistrationDTO personRegistration) {
        Person person = personRegistration.toPerson();
        Optional.ofNullable(person.getCpf())
                .filter(StringUtils::isNotBlank)
                .flatMap(personRepository::findPersonIdByCpf)
                .ifPresent(person::setPersonId);
        return person;
    }

}
