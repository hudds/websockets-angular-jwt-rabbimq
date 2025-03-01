package dev.hudsonprojects.backend;

import dev.hudsonprojects.backend.integration.coursesapi.person.registration.PersonRegistrationDTO;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.queue.PersonRegistrationQueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Criado para testar configuracao, irei remover
 */
@Deprecated(forRemoval = true)
@RequestMapping("test")
@RestController
public class TestEndpoint {

    private static String instanceId;


    private final PersonRegistrationQueueSender personRegistrationQueueSender;

    @Autowired
    public TestEndpoint(PersonRegistrationQueueSender personRegistrationQueueSender) {
        this.personRegistrationQueueSender = personRegistrationQueueSender;

    }

    @PostMapping("rabbitmq/person/registration")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMessage(@RequestBody PersonRegistrationDTO message){
        personRegistrationQueueSender.send(message);
    }


    @GetMapping("instance-id")
    @ResponseStatus(HttpStatus.OK)
    public synchronized String getInstanceId(){
        if(instanceId == null){
            instanceId = UUID.randomUUID().toString();
        }
        return instanceId;
    }
}
