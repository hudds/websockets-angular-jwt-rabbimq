package dev.hudsonprojects.api.webhook.entity;

import dev.hudsonprojects.api.appuser.AppUser;
import dev.hudsonprojects.api.common.entity.DefaultEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "webhook")
public class Webhook extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "webhook_id")
    private Long webhookId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "webhook_id")
    private List<WebhookTopic> topics;

    @OneToOne
    @JoinColumn(name = "http_request_data_id")
    private HttpRequestData httpRequestData;

    @Column(name = "owner_id", nullable = false, updatable = false)
    private Long ownerId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false, updatable = false, insertable = false)
    private AppUser owner;

    private boolean active;

    public Webhook() {}

    public Webhook(Webhook webhook) {
        super(webhook);
        webhookId = webhook.webhookId;
        ownerId = webhook.ownerId;
        active = webhook.active;
        httpRequestData = webhook.httpRequestData == null ? null : webhook.httpRequestData.createCopy();
        owner = webhook.owner;
        topics = webhook.topics != null ? new ArrayList<>(webhook.topics.stream()
                .map(topic -> new WebhookTopic(topic, this))
                .toList()) : null;
    }

    public Webhook createCopy() {
        return new Webhook(this);
    }

    public Long getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(Long webhookId) {
        this.webhookId = webhookId;
    }

    public List<WebhookTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<WebhookTopic> topics) {
        this.topics = topics;
    }

    public HttpRequestData getHttpRequestData() {
        return httpRequestData;
    }

    public void setHttpRequestData(HttpRequestData httpRequestData) {
        this.httpRequestData = httpRequestData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Webhook webhook = (Webhook) o;
        return Objects.equals(webhookId, webhook.webhookId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(webhookId);
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
