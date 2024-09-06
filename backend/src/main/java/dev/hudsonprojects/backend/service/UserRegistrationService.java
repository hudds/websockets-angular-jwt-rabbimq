package dev.hudsonprojects.backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hudsonprojects.backend.dto.UserRegistrationDTO;
import dev.hudsonprojects.backend.exception.ValidationException;
import dev.hudsonprojects.backend.lib.GenericValidator;
import dev.hudsonprojects.backend.lib.messages.builder.APIFieldError;
import dev.hudsonprojects.backend.lib.messages.builder.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.lib.util.CPFUtil;
import dev.hudsonprojects.backend.lib.util.EmailUtils;
import dev.hudsonprojects.backend.lib.util.PasswordUtils;
import static dev.hudsonprojects.backend.lib.util.StringUtils.isBlank;
import static dev.hudsonprojects.backend.lib.util.StringUtils.isLettersOnly;
import static dev.hudsonprojects.backend.lib.util.StringUtils.isNotBlank;
import static dev.hudsonprojects.backend.lib.util.StringUtils.wordCount;
import dev.hudsonprojects.backend.model.entity.AppUser;
import dev.hudsonprojects.backend.model.entity.CredentialsType;
import dev.hudsonprojects.backend.repository.AppUserRepository;

@Service
@Transactional
public class UserRegistrationService {

    private final CredentialService credentialsService;

    private final AppUserRepository appUserRepository;

    public UserRegistrationService(CredentialService credentialsService, AppUserRepository appUserRepository) {
        this.credentialsService = credentialsService;
        this.appUserRepository = appUserRepository;
    }

    public void registerUser(UserRegistrationDTO userRegistration){
        AppUser appUser = userRegistration.toAppUser();
        List<APIFieldError> errors = validate(appUser);
        if(!errors.isEmpty()){
            throw new ValidationException(ErrorDetailsBuilder
                .builder()
                .setMessage("validation.user.invalid")
                .setFieldErrors(errors)
                .build()
            );
        }
        appUser.setExternalId(UUID.randomUUID().toString());
        appUser.getCredentials().setIdentifier(appUser.getExternalId());
        appUser.getCredentials().setCredentialsType(CredentialsType.USER);
        credentialsService.encodeCredentials(appUser.getCredentials());
        appUserRepository.save(appUser);
    }

    private List<APIFieldError> validate(AppUser appUser){
        List<APIFieldError> errors = getAppUserValuesValidator().applyValidations(appUser);
        if(errors.isEmpty()) {
            errors.addAll(getAppUserValidator().applyValidations(appUser));
        }
        return errors;
    }

    private GenericValidator<AppUser> getAppUserValidator(){
        return  GenericValidator.builder(AppUser.class)
            .addValidation(
                "cpf", 
                "validation.user.cpf.exists", 
                user -> appUserRepository.existsByCpf(user.getCpf())
            ).addValidation(
                "cpf", 
                "validation.user.email.exists", 
                user -> user.getEmail() == null || appUserRepository.existsByEmail(user.getEmail())
            ).build();
    }


    private static boolean isEmailValid(AppUser user) {
        return user.getEmail() == null || EmailUtils.isEmailValid(user.getEmail());
    }
    
    private static GenericValidator<AppUser> getAppUserValuesValidator(){
        return  GenericValidator.builder(AppUser.class)
            .addValidation(
                "name",
                "validation.user.name.required",
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
            ).addValidation(
                "credentials.password", 
                "validations.user.credentials.password.required", 
                user -> user.getCredentials() != null && isNotBlank(user.getCredentials().getPassword())
            ).addValidation(
                "credentials.password", 
                "validation.StrongPassword", 
                user -> user.getCredentials() == null || isBlank(user.getCredentials().getPassword()) || PasswordUtils.isStrong(user.getCredentials().getPassword())
            ).build();
    }

}
