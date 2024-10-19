package dev.hudsonprojects.backend.appuser.integration.coursesapi.registration;

import dev.hudsonprojects.backend.appuser.AppUserRepository;
import dev.hudsonprojects.backend.appuser.integration.coursesapi.registration.queue.PersonQueueSender;
import dev.hudsonprojects.backend.appuser.registration.event.AppUserCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class PersonRegistrationCoursesAPIService {

    private final AppUserRepository appUserRepository;

    private final PersonQueueSender personQueueSender;

    @Autowired
    public PersonRegistrationCoursesAPIService(AppUserRepository appUserRepository, PersonQueueSender personQueueSender) {
        this.appUserRepository = appUserRepository;
        this.personQueueSender = personQueueSender;
    }

    @TransactionalEventListener
    public void onAppUserCreated(AppUserCreated appUserCreated){
        appUserRepository.findById(appUserCreated.userId())
                .map(PersonRegistrationDTO::new)
                .ifPresent(personQueueSender::send);
    }



    public void register(PersonRegistrationDTO personRegistrationDTO) {

    }
}
