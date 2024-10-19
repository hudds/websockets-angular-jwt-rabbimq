package dev.hudsonprojects.backend.appuser.integration.coursesapi.registration.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.appuser.integration.coursesapi.registration.PersonRegistrationDTO;
import dev.hudsonprojects.backend.common.messagequeue.QueueSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonQueueSender extends QueueSender<PersonRegistrationDTO> {


    @Autowired
    public PersonQueueSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        super(rabbitTemplate, objectMapper);
    }


    @Override
    protected String getQueueName() {
        return PersonQueueConfiguration.QUEUE_NAME;
    }
}
