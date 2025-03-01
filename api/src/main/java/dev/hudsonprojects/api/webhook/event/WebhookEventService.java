package dev.hudsonprojects.api.webhook.event;

import dev.hudsonprojects.api.webhook.HttpHeaderDTO;
import dev.hudsonprojects.api.webhook.WebhookDTO;
import dev.hudsonprojects.api.webhook.notification.WebhookNotification;
import dev.hudsonprojects.api.webhook.notification.queue.WebhookNotificationSender;
import dev.hudsonprojects.api.webhook.repository.WebhookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WebhookEventService {

    private final WebhookRepository webHookRepository;
    private final WebhookNotificationSender webhookNotificationSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookEventService.class);

    @Autowired
    public WebhookEventService(WebhookRepository webHookRepository, WebhookNotificationSender webhookNotificationSender) {
        this.webHookRepository = webHookRepository;
        this.webhookNotificationSender = webhookNotificationSender;
    }

    public void publish(WebhookEvent webhookEvent) {
        List<WebhookDTO> listeners = webHookRepository.getWebhookDTOByTopic(webhookEvent.getTopic());
        Map<WebhookNotification, Set<Long>> webhooksByNotification = new HashMap<>();
        for (var listener : listeners) {
            WebhookNotification notification = new WebhookNotification();
            notification.setUrl(listener.getUrl());
            notification.setMethod(listener.getMethod());
            notification.setBody(webhookEvent.getContent());
            notification.setHeaders(getHeaderMap(listener));
            webhooksByNotification.computeIfAbsent(notification, key -> new HashSet<>()).add(listener.getWebhookId());
        }

        for(var notification : webhooksByNotification.keySet()) {
            notification.setWebhookIds(webhooksByNotification.get(notification));
            try {
                webhookNotificationSender.send(notification);
            } catch (Exception e) {
                LOGGER.error("Failed to send " + WebhookNotification.class + ". webhookIds: " + webhooksByNotification.get(notification), e);
            }
        }
    }

    private Map<String, String> getHeaderMap(WebhookDTO httpRequestData) {
        if(httpRequestData.getHeaders() != null) {
            return httpRequestData.getHeaders().stream()
                    .collect(Collectors.toMap(HttpHeaderDTO::getName, HttpHeaderDTO::getValue, (existing, replacement) -> existing));
        }
        return null;
    }

}
