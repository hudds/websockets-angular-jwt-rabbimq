package dev.hudsonprojects.backend.appuser;




import static dev.hudsonprojects.backend.common.lib.util.StringUtils.isBlank;
import static dev.hudsonprojects.backend.common.lib.util.StringUtils.isLettersOnly;
import static dev.hudsonprojects.backend.common.lib.util.StringUtils.isNotBlank;
import static dev.hudsonprojects.backend.common.lib.util.StringUtils.wordCount;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.hudsonprojects.backend.common.lib.util.CPFUtil;
import dev.hudsonprojects.backend.common.lib.util.EmailUtils;
import dev.hudsonprojects.backend.common.lib.util.PasswordUtils;
import dev.hudsonprojects.backend.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.backend.common.validation.GenericValidator;

@Service
public class AppUserValidationService {

    private final AppUserRepository appUserRepository;

    public AppUserValidationService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<APIFieldError> validate(AppUser appUser) {
        List<APIFieldError> errors = getAppUserValuesValidator().applyValidations(appUser);
        if (errors.isEmpty()) {
            errors.addAll(getAppUserValidator().applyValidations(appUser));
        }
        return errors;
    }

    
    private GenericValidator<AppUser> getAppUserValidator(){
        return GenericValidator.builder(AppUser.class)
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

    private boolean notExistsUserWithCpf(AppUser appUser){
        return !existsUserWithCpf(appUser);
    }

    private boolean existsUserWithCpf(AppUser appUser){
        if(appUser.getCpf() == null){
            return false;
        }
        if(appUser.getUserId() != null){
            return appUserRepository.existsByCpfAndUserIdNot(appUser.getCpf(), appUser.getUserId());
        }
        return appUserRepository.existsByCpf(appUser.getCpf());
    }


    private boolean notExistsUserWithEmail(AppUser appUser) {
        return !existsUserWithEmail(appUser);
    }

    private boolean existsUserWithEmail(AppUser appUser) {
        if(appUser.getEmail() == null){
            return false;
        }
        if(appUser.getUserId() != null){
            return appUserRepository.existsByEmailAndUserIdNot(appUser.getEmail(), appUser.getUserId());
        }
        return appUserRepository.existsByEmail(appUser.getEmail());
    }


    private static boolean isEmailValid(AppUser user) {
        return user.getEmail() == null || EmailUtils.isEmailValid(user.getEmail());
    }

    private static GenericValidator<AppUser> getAppUserValuesValidator() {
        return GenericValidator.builder(AppUser.class)
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
