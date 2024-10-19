package dev.hudsonprojects.api.person;


import static dev.hudsonprojects.api.common.lib.util.StringUtils.isBlank;
import static dev.hudsonprojects.api.common.lib.util.StringUtils.isLettersOnly;
import static dev.hudsonprojects.api.common.lib.util.StringUtils.isNotBlank;
import static dev.hudsonprojects.api.common.lib.util.StringUtils.wordCount;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.hudsonprojects.api.common.lib.util.CPFUtil;
import dev.hudsonprojects.api.common.lib.util.EmailUtils;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.common.validation.GenericValidator;

@Service
public class PersonValidationService {

    private final PersonRepository personRepository;

    public PersonValidationService(PersonRepository PersonRepository) {
        this.personRepository = PersonRepository;
    }

    public List<APIFieldError> validate(Person Person) {
        List<APIFieldError> errors = getPersonValuesValidator().applyValidations(Person);
        if (errors.isEmpty()) {
            errors.addAll(getPersonValidator().applyValidations(Person));
        }
        return errors;
    }

    
    private GenericValidator<Person> getPersonValidator(){
        return GenericValidator.builder(Person.class)
            .addValidation(
                "cpf", 
                "validation.user.cpf.exists", 
                this::notExistsUserWithCpf
            ).addValidation(
                "email", 
                "validation.user.email.exists", 
                this::notExistsUserWithEmail
            ).build();
    }

    private boolean notExistsUserWithCpf(Person Person){
        return !existsUserWithCpf(Person);
    }

    private boolean existsUserWithCpf(Person Person){
        if(Person.getCpf() == null){
            return false;
        }
        if(Person.getPersonId() != null){
            return personRepository.existsByCpfAndPersonIdNot(Person.getCpf(), Person.getPersonId());
        }
        return personRepository.existsByCpf(Person.getCpf());
    }


    private boolean notExistsUserWithEmail(Person Person) {
        return !existsUserWithEmail(Person);
    }

    private boolean existsUserWithEmail(Person Person) {
        if(Person.getEmail() == null){
            return false;
        }
        if(Person.getPersonId() != null){
            return personRepository.existsByEmailAndPersonIdNot(Person.getEmail(), Person.getPersonId());
        }
        return personRepository.existsByEmail(Person.getEmail());
    }


    private static boolean isEmailValid(Person user) {
        return user.getEmail() == null || EmailUtils.isEmailValid(user.getEmail());
    }

    private static GenericValidator<Person> getPersonValuesValidator() {
        return GenericValidator.builder(Person.class)
                .addValidation(
                        "name",
                        "validation.person.name.required",
                        user -> isNotBlank(user.getName())
                ).addValidation(
                        "name",
                        "validation.user.name.invalid",
                        user -> isBlank(user.getName()) || isLettersOnly(user.getName())
                ).addValidation(
                        "name",
                        "validation.user.name.fullName",
                        user -> isBlank(user.getName()) || wordCount(user.getName()) > 1
                ).addValidation(
                        "email",
                        "validation.user.email.invalid",
                        user -> isEmailValid(user)
                ).addValidation(
                        "cpf",
                        "validation.user.cpf.required",
                        user -> isNotBlank(user.getCpf())
                ).addValidation(
                        "cpf",
                        "validation.CPF.invalid",
                        user -> isBlank(user.getCpf()) || CPFUtil.isCPF(user.getCpf())
                ).addValidation(
                        "birthDate",
                        "validation.NotFuture",
                        user -> user.getBirthDate() == null || !user.getBirthDate().isAfter(LocalDate.now())
                ).build();
    }

}
