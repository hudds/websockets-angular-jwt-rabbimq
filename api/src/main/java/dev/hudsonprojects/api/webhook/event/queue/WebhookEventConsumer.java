package dev.hudsonprojects.api.webhook.event.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.api.messagequeue.QueueConsumer;
import dev.hudsonprojects.api.webhook.event.WebhookEvent;
import dev.hudsonprojects.api.webhook.event.WebhookEventService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebhookEventConsumer extends QueueConsumer<WebhookEvent> {

    private final WebhookEventService webhookEventService;

    @Autowired
    public WebhookEventConsumer(ObjectMapper objectMapper, WebhookEventService webhookEventService) {
        super(objectMapper);
        this.webhookEventService = webhookEventService;
    }

    @Override
    @RabbitListener(queues = {WebhookEventQueueConfiguration.QUEUE_NAME}, concurrency = "5")
    public void receive(String message) {
        super.convertAndProcessMessage(message);
    }

    @Override
    protected Class<WebhookEvent> getMessageType() {
        return WebhookEvent.class;
    }

    @Override
    protected void processMessage(WebhookEvent message) {
        webhookEventService.publish(message);
    }
}
