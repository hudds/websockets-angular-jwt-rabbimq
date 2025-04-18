package dev.hudsonprojects.api.appuser;

import dev.hudsonprojects.api.security.credentials.CredentialService;
import dev.hudsonprojects.api.security.credentials.Credentials;
import dev.hudsonprojects.api.security.credentials.CredentialsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultAppUserListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAppUserListener.class);

    private final DefaultAppUserService defaultAppUserService;


    @Autowired
    public DefaultAppUserListener(DefaultAppUserService defaultAppUserService){
        this.defaultAppUserService = defaultAppUserService;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void createOrUpdateDefaultUser() {
        try {
            defaultAppUserService.createOrUpdateDefaultUser();
        } catch (Exception e){
            LOGGER.error("Failed to register default username and password", e);
        }
    }

}
