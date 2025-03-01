package dev.hudsonprojects.api.webhook.entity;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "public", name = "webhook_topic")
public class WebhookTopic extends DefaultEntity {
    @EmbeddedId
    private WebhookTopicId id = new WebhookTopicId();
    @ManyToOne
    @JoinColumn(name = "webhook_id", nullable = false, updatable = false, insertable = false)
    private Webhook webhook;

    public WebhookTopic() {}

    public WebhookTopic(Webhook webhook, String topic) {
        this.webhook = webhook;
        getId().setWebhookId(webhook != null ? webhook.getWebhookId() : null);
        getId().setTopic(topic);
    }

    public WebhookTopic(WebhookTopic webhookTopic, Webhook webhook) {
        super(webhookTopic);
        id = webhookTopic.id != null ? new WebhookTopicId(webhookTopic.id) : null;
        this.webhook = webhook;
    }

    public WebhookTopicId getId() {
        return id;
    }

    public void setId(WebhookTopicId id) {
        this.id = id;
    }

    public String getTopic() {
        return getId().getTopic();
    }

    public void setTopic(String topic) {
        getId().setTopic(topic);
    }

    public Webhook getWebhook() {
        return webhook;
    }

    public void setWebhook(Webhook webhook) {
        this.webhook = webhook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebhookTopic that = (WebhookTopic) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
