package dev.hudsonprojects.backend.appuser.registration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import dev.hudsonprojects.backend.appuser.registration.event.AppUserCreated;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.appuser.AppUserRepository;
import dev.hudsonprojects.backend.appuser.AppUserValidationService;
import dev.hudsonprojects.backend.common.exception.ValidationException;
import dev.hudsonprojects.backend.common.lib.PersonUsernameGenerator;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.backend.security.credentials.CredentialService;
import dev.hudsonprojects.backend.security.credentials.CredentialsType;

@Service
public class UserRegistrationService {

    private final CredentialService credentialsService;

    private final AppUserRepository appUserRepository;

    private final AppUserValidationService userValidationService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public UserRegistrationService(CredentialService credentialsService, AppUserRepository appUserRepository, AppUserValidationService userValidationService, ApplicationEventPublisher applicationEventPublisher) {
        this.credentialsService = credentialsService;
        this.appUserRepository = appUserRepository;
        this.userValidationService = userValidationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public AppUserDTO registerUser(UserRegistrationDTO userRegistration){
        AppUser appUser = userRegistration.toAppUser();
        List<APIFieldError> errors = userValidationService.validate(appUser);
        if(!errors.isEmpty()){
            throw new ValidationException(ErrorDetailsBuilder
                .builder()
                .setMessage("validation.user.invalid")
                .setFieldErrors(errors)
                .build()
            );
        }

        appUser.setUsername(generateUsername(appUser.getName()));
        appUser.getCredentials().setIdentifier(appUser.getUsername());
        appUser.getCredentials().setCredentialsType(CredentialsType.USER);
        credentialsService.save(appUser.getCredentials());
        appUserRepository.save(appUser);
        applicationEventPublisher.publishEvent(new AppUserCreated(appUser.getUserId()));
        return new AppUserDTO(appUser);
    }
 
    private String generateUsername(String fullName){
        Set<String> usernames = new PersonUsernameGenerator(fullName).getUsernames();
        Set<String> existingUsernames = new HashSet<>(appUserRepository.getExistingUsernames(usernames));
        String newUsername = usernames.stream()
            .filter(username -> !existingUsernames.contains(username))
            .sorted(Comparator.comparing(String::length))
            .findFirst()
            .orElse(null);

        if(newUsername != null){
            return newUsername;
        }

        List<String> usernameList = new ArrayList<>(usernames);
        Random random = new Random();
        String username = usernameList.get(random.nextInt(0, usernameList.size()));
        List<String> usernameTests = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
        	usernameTests.add(username + "@" +Long.toHexString(random.nextLong((long) Math.pow(10L, i), (long) Math.pow(10L, i+1))));	
        }
        usernameTests.add(username + "@" + Long.toHexString(System.nanoTime()));
        usernameTests.add(username + "@" + UUID.randomUUID());
        Set<String> testedUsernames = new HashSet<>(appUserRepository.getExistingUsernames(usernameTests));
        return usernameTests.stream()
        		.filter(testedUsername -> !testedUsernames.contains(testedUsername))
                .sorted(Comparator.comparing(String::length))
                .findFirst()
                .orElse(username + "@" + UUID.randomUUID());

        
    }

}
