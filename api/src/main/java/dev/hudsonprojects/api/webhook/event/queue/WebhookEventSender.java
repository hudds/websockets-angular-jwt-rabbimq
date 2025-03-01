package dev.hudsonprojects.api.webhook.event.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.api.messagequeue.QueueSender;
import dev.hudsonprojects.api.webhook.event.WebhookEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebhookEventSender extends QueueSender<WebhookEvent> {

    @Autowired
    public WebhookEventSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        super(rabbitTemplate, objectMapper);
    }

    @Override
    protected String getQueueName() {
        return WebhookEventQueueConfiguration.QUEUE_NAME;
    }
}
