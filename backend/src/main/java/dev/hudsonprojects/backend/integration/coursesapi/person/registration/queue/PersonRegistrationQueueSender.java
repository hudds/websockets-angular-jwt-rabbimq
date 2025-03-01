package dev.hudsonprojects.backend.integration.coursesapi.person.registration.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.PersonRegistrationDTO;
import dev.hudsonprojects.backend.common.messagequeue.QueueSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonRegistrationQueueSender extends QueueSender<PersonRegistrationDTO> {


    @Autowired
    public PersonRegistrationQueueSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        super(rabbitTemplate, objectMapper);
    }


    @Override
    protected String getQueueName() {
        return PersonRegistrationQueueConfiguration.QUEUE_NAME;
    }
}
