package dev.hudsonprojects.api.webhook;

import dev.hudsonprojects.api.common.lib.util.DateTimeUtils;
import dev.hudsonprojects.api.webhook.entity.HttpHeader;
import dev.hudsonprojects.api.webhook.entity.HttpRequestData;
import dev.hudsonprojects.api.webhook.entity.Webhook;
import dev.hudsonprojects.api.webhook.entity.WebhookTopic;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebhookDTO {

    private Long webhookId;
    private String url;
    private String method;
    private List<HttpHeaderDTO> headers;
    private List<String> topics;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long ownerId;

    public WebhookDTO() {}

    public WebhookDTO(Webhook webhook) {
        this.webhookId = webhook.getWebhookId();
        this.active = webhook.isActive();
        this.createdAt = webhook.getCreatedAt();
        this.ownerId = webhook.getOwnerId();
        List<LocalDateTime> updateDates = new ArrayList<>();
        updateDates.add(webhook.getUpdatedAt());
        if (webhook.getTopics() != null){
            topics = webhook.getTopics().stream().map(WebhookTopic::getTopic).toList();
            webhook.getTopics().stream().map(WebhookTopic::getCreatedAt).forEach(updateDates::add);
            webhook.getTopics().stream().map(WebhookTopic::getUpdatedAt).forEach(updateDates::add);
        }
        HttpRequestData httpRequestData = webhook.getHttpRequestData();
        if(httpRequestData != null) {
            this.url = httpRequestData.getUrl();
            this.method = httpRequestData.getMethod();
            if(httpRequestData.getHeaders() != null) {
                this.headers = httpRequestData.getHeaders().stream()
                        .map(HttpHeaderDTO::new)
                        .toList();
                httpRequestData.getHeaders().stream().map(HttpHeader::getCreatedAt).forEach(updateDates::add);
                httpRequestData.getHeaders().stream().map(HttpHeader::getUpdatedAt).forEach(updateDates::add);
            }
            updateDates.add(httpRequestData.getUpdatedAt());
        }
        this.updatedAt = updateDates.stream().filter(Objects::nonNull).max(LocalDateTime::compareTo).orElse(null);
    }

    public Long getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(Long webhookId) {
        this.webhookId = webhookId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<HttpHeaderDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeaderDTO> headers) {
        this.headers = headers;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }


}
