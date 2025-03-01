package dev.hudsonprojects.backend.integration.coursesapi.person.registration;

import dev.hudsonprojects.backend.appuser.AppUserRepository;
import dev.hudsonprojects.backend.appuser.integration.AppUserIntegrationProtocol;
import dev.hudsonprojects.backend.appuser.integration.AppUserIntegrationProtocolRepository;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.exception.CoursesAPIPersonRegistrationException;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.queue.PersonRegistrationQueueSender;
import dev.hudsonprojects.backend.appuser.registration.event.AppUserCreated;
import dev.hudsonprojects.backend.integration.exception.HttpIntegrationException;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocol;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocolRepository;
import dev.hudsonprojects.backend.integration.protocol.IntegrationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Service
public class PersonRegistrationCoursesAPIService {

    private final AppUserRepository appUserRepository;
    private final IntegrationHttpProtocolRepository integrationHttpProtocolRepository;
    private final AppUserIntegrationProtocolRepository appUserIntegrationProtocolRepository;

    private final PersonRegistrationQueueSender personRegistrationQueueSender;

    private final PersonRegistrationCoursesAPIClient personRegistrationCoursesAPIClient;

    @Autowired
    public PersonRegistrationCoursesAPIService(AppUserRepository appUserRepository, IntegrationHttpProtocolRepository integrationHttpProtocolRepository, AppUserIntegrationProtocolRepository appUserIntegrationProtocolRepository, PersonRegistrationQueueSender personRegistrationQueueSender, PersonRegistrationCoursesAPIClient personRegistrationCoursesAPIClient) {
        this.appUserRepository = appUserRepository;
        this.integrationHttpProtocolRepository = integrationHttpProtocolRepository;
        this.appUserIntegrationProtocolRepository = appUserIntegrationProtocolRepository;
        this.personRegistrationQueueSender = personRegistrationQueueSender;
        this.personRegistrationCoursesAPIClient = personRegistrationCoursesAPIClient;
    }

    @Async
    @TransactionalEventListener
    public void onAppUserCreated(AppUserCreated appUserCreated){
        appUserRepository.findById(appUserCreated.userId())
                .map(PersonRegistrationDTO::new)
                .ifPresent(personRegistrationQueueSender::send);
    }


    public void sendUserByCPF(String cpf){
        appUserRepository.findByCpf(cpf)
                .map(PersonRegistrationDTO::new)
                .ifPresent(personRegistrationQueueSender::send);
    }

    @Transactional
    public void register(PersonRegistrationDTO personRegistrationDTO) throws CoursesAPIHttpException {
        IntegrationHttpProtocol protocol = new IntegrationHttpProtocol();
        protocol.setIntegrationStatus(IntegrationStatus.PENDING);
        protocol.setSentAt(LocalDateTime.now());
        integrationHttpProtocolRepository.save(protocol);
        CoursesAPIHttpException integrationException = null;
        try {
            personRegistrationCoursesAPIClient.createByCpf(personRegistrationDTO, protocol);
        } catch (CoursesAPIHttpException e) {
            protocol.setIntegrationStatus(IntegrationStatus.ERROR);
            protocol.setResponseStatus(e.getStatusCode());
            protocol.setResponse(e.getResponseBody());
            protocol.setExceptionMessage(e.getMessage());
            integrationException = e;
        } catch (Exception e) {
            protocol.setIntegrationStatus(IntegrationStatus.ERROR);
            protocol.setExceptionMessage(e.getMessage());
            integrationException = new CoursesAPIHttpException(e);
        } finally {
            protocol.setResponseReceivedAt(LocalDateTime.now());
        }

        integrationHttpProtocolRepository.save(protocol);
        appUserRepository.findByCpf(personRegistrationDTO.getCpf()).ifPresent(appUser -> {
            AppUserIntegrationProtocol appUserIntegrationProtocol = new AppUserIntegrationProtocol();
            appUserIntegrationProtocol.setIntegrationHttpProtocol(protocol);
            appUserIntegrationProtocol.setAppUser(appUser);
            appUserIntegrationProtocolRepository.save(appUserIntegrationProtocol);
        });
        if(integrationException != null){
            throw integrationException;
        }
    }
}
