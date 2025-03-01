package dev.hudsonprojects.api.webhook.notification.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.api.messagequeue.QueueConsumer;
import dev.hudsonprojects.api.webhook.notification.WebhookNotification;
import dev.hudsonprojects.api.webhook.notification.WebhookNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebhookNotificationConsumer extends QueueConsumer<WebhookNotification> {

    private final WebhookNotificationService webhookNotificationService;

    @Autowired
    public WebhookNotificationConsumer(ObjectMapper objectMapper, WebhookNotificationService webhookNotificationService) {
        super(objectMapper);
        this.webhookNotificationService = webhookNotificationService;
    }

    @Override
    @RabbitListener(queues = {WebhookNotificationQueueConfiguration.QUEUE_NAME})
    public void receive(String message) {
        super.convertAndProcessMessage(message);
    }

    @Override
    protected Class<WebhookNotification> getMessageType() {
        return WebhookNotification.class;
    }

    @Override
    protected void processMessage(WebhookNotification message) {
        webhookNotificationService.sendNotification(message);
    }
}
