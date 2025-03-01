package dev.hudsonprojects.backend.integration.coursesapi.person.registration.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.messagequeue.exception.MessageProcessingException;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.PersonRegistrationCoursesAPIService;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.PersonRegistrationDTO;
import dev.hudsonprojects.backend.common.messagequeue.QueueConsumer;
import dev.hudsonprojects.backend.integration.exception.IntegrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PersonRegistrationQueueConsumer extends QueueConsumer<PersonRegistrationDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRegistrationQueueConsumer.class);

    private final PersonRegistrationCoursesAPIService personRegistrationCoursesAPIService;

    @Autowired
    protected PersonRegistrationQueueConsumer(ObjectMapper objectMapper, PersonRegistrationCoursesAPIService personRegistrationCoursesAPIService) {
        super(objectMapper);
        this.personRegistrationCoursesAPIService = personRegistrationCoursesAPIService;
    }

    @Override
    @RabbitListener(queues = {PersonRegistrationQueueConfiguration.QUEUE_NAME})
    public void receive(@Payload String message){
        super.convertAndProcessMessage(message);
    }

    @Override
    protected Class<PersonRegistrationDTO> getMessageType() {
        return PersonRegistrationDTO.class;
    }

    @Override
    protected void processMessage(PersonRegistrationDTO message) {
        try {
            personRegistrationCoursesAPIService.register(message);
        } catch (CoursesAPIHttpException e) {
            throw new MessageProcessingException(e);
        }
    }

}
