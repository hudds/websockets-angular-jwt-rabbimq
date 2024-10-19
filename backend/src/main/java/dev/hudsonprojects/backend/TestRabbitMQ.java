package dev.hudsonprojects.backend;

import dev.hudsonprojects.backend.appuser.integration.coursesapi.registration.PersonRegistrationDTO;
import dev.hudsonprojects.backend.appuser.integration.coursesapi.registration.queue.PersonQueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Criado para testar configuracao, irei remover
 */
@Deprecated(forRemoval = true)
@RequestMapping("test")
@RestController
public class TestRabbitMQ {


    private final PersonQueueSender personQueueSender;

    @Autowired
    public TestRabbitMQ(PersonQueueSender personQueueSender) {
        this.personQueueSender = personQueueSender;
    }

    @PostMapping("rabbitmq/person/registration")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMessage(@RequestBody PersonRegistrationDTO message){
        personQueueSender.send(message);
    }
}
