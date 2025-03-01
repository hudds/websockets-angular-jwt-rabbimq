package dev.hudsonprojects.api.webhook.notification.queue;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebhookNotificationQueueConfiguration {

    public static final String QUEUE_NAME = "api-webhook-notification";

    @Bean
    public Queue webhookNotificationQueue(AmqpAdmin amqpAdmin) {
        Queue queue = new Queue(QUEUE_NAME, true, false, false);
        amqpAdmin.declareQueue(queue);
        return queue;
    }
}
