package dev.hudsonprojects.backend.integration.coursesapi.subscription.queue;


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionQueueConfiguration {
    public static final String QUEUE_NAME = "coursesapi-subscription";


    @Bean
    public Queue subscriptionQueue(AmqpAdmin amqpAdmin){
        Queue queue = new Queue(QUEUE_NAME, true, false, false);
        amqpAdmin.declareQueue(queue);
        return queue;
    }
}
