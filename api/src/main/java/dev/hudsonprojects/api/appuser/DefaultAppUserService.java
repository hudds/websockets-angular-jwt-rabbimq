package dev.hudsonprojects.api.appuser;

import dev.hudsonprojects.api.security.credentials.CredentialService;
import dev.hudsonprojects.api.security.credentials.Credentials;
import dev.hudsonprojects.api.security.credentials.CredentialsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultAppUserService {

    private final String enableDefaultUser;
    private final String defaultUsername;
    private final String defaultPassword;
    private final AppUserRepository appUserRepository;
    private final CredentialService credentialService;

    @Autowired
    public DefaultAppUserService(
            @Value("${application.user.default.enable}") String enableDefaultUser,
            @Value("${application.user.default.username}") String defaultUsername,
            @Value("${application.user.default.password}") String defaultPassword,
            AppUserRepository appUserRepository, CredentialService credentialService) {
        this.enableDefaultUser = enableDefaultUser;
        this.defaultUsername = defaultUsername;
        this.defaultPassword = defaultPassword;
        this.appUserRepository = appUserRepository;
        this.credentialService = credentialService;
    }


    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void createOrUpdateDefaultUser() {
        if(!Boolean.parseBoolean(enableDefaultUser)){
            return;
        }
        AppUser defaultUser = appUserRepository.findByUsername(defaultUsername).orElseGet(() -> {
            AppUser user = new AppUser();
            Credentials credentials = new Credentials();
            credentials.setIdentifier(user.getUsername());
            credentials.setCredentialsType(CredentialsType.USER);
            user.setCredentials(credentials);
            return user;
        });
        defaultUser.setUsername(defaultUsername);
        defaultUser.getCredentials().setPassword(defaultPassword);
        defaultUser.getCredentials().setIdentifier(defaultUser.getUsername());
        appUserRepository.save(defaultUser);
        credentialService.save(defaultUser.getCredentials());
    }



}
