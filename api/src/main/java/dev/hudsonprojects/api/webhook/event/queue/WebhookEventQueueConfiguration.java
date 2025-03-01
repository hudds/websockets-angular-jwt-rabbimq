package dev.hudsonprojects.api.webhook.event.queue;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebhookEventQueueConfiguration {

    public static final String QUEUE_NAME = "api-webhook-event";

    @Bean
    public Queue webhookEventQueue(AmqpAdmin amqpAdmin) {
        Queue queue = new Queue(QUEUE_NAME, true, false, false);
        amqpAdmin.declareQueue(queue);
        return queue;
    }
}
