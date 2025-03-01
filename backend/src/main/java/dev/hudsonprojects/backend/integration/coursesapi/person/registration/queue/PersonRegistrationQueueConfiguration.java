package dev.hudsonprojects.backend.integration.coursesapi.person.registration.queue;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonRegistrationQueueConfiguration {
    public static final String QUEUE_NAME = "coursesapi-person-registration";


    @Bean
    public Queue personQueue(AmqpAdmin amqpAdmin){
        Queue queue = new Queue(QUEUE_NAME, true, false, false);
        amqpAdmin.declareQueue(queue);
        return queue;
    }
}
