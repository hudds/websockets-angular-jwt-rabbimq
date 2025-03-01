package dev.hudsonprojects.api.webhook.notification.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.api.messagequeue.QueueSender;
import dev.hudsonprojects.api.webhook.notification.WebhookNotification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebhookNotificationSender extends QueueSender<WebhookNotification> {

    @Autowired
    public WebhookNotificationSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        super(rabbitTemplate, objectMapper);
    }

    @Override
    protected String getQueueName() {
        return WebhookNotificationQueueConfiguration.QUEUE_NAME;
    }
}
