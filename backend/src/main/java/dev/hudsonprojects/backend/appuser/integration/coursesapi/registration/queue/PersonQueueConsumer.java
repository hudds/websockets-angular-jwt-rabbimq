package dev.hudsonprojects.backend.appuser.integration.coursesapi.registration.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.appuser.integration.coursesapi.registration.PersonRegistrationDTO;
import dev.hudsonprojects.backend.common.messagequeue.QueueConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PersonQueueConsumer extends QueueConsumer<PersonRegistrationDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonQueueConsumer.class);

    @Autowired
    protected PersonQueueConsumer(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @RabbitListener(queues = {PersonQueueConfiguration.QUEUE_NAME})
    public void receive(@Payload String message){
        super.convertAndProcessMessage(message);
    }

    @Override
    protected Class<PersonRegistrationDTO> getMessageType() {
        return PersonRegistrationDTO.class;
    }

    @Override
    protected void processMessage(PersonRegistrationDTO message) {
        // TODO enviar pessoa para a API
        LOGGER.info("Person message received. Name: {}, CPF: {}", message.getName(), message.getCpf());
    }

}
