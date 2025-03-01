package dev.hudsonprojects.api.webhook.entity;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import dev.hudsonprojects.api.common.entity.RegistryData;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class WebhookTopicId {
    @Column(name = "topic", nullable = false, updatable = false)
    private String topic;
    @Column(name = "webhook_id", nullable = false, updatable = false)
    private Long webhookId;

    public WebhookTopicId(){}

    public WebhookTopicId(WebhookTopicId id) {
        topic = id.topic;
        webhookId = id.webhookId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(Long webhookId) {
        this.webhookId = webhookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebhookTopicId that = (WebhookTopicId) o;
        return Objects.equals(topic, that.topic) && Objects.equals(webhookId, that.webhookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, webhookId);
    }

    @Override
    public String toString() {
        return "WebhookTopicId{" +
                "topic='" + topic + '\'' +
                ", webhookId=" + webhookId +
                '}';
    }
}
