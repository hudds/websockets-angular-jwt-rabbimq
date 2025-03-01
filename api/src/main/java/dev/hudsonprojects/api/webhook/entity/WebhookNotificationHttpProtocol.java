package dev.hudsonprojects.api.webhook.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "webhook_notification_protocol")
public class WebhookNotificationHttpProtocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long webhookNotificationProtocolId;
    @Column(columnDefinition="TEXT")
    private String url;
    private String method;
    @Column(columnDefinition="TEXT")
    private String body;
    @Column(columnDefinition="TEXT")
    private String headers;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private WebhookNotificationProtocolStatus status;
    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
    @Column(name = "response_received_at")
    private LocalDateTime responseReceivedAt;
    @Column(name = "webhook_id", nullable = false)
    private Long webhookId;
    @ManyToOne
    @JoinColumn(name = "webhook_id", updatable = false, insertable = false)
    private Webhook webhook;
    @Column(columnDefinition="TEXT")
    private String responseHeaders;
    @Column(name = "response_body", length = 10000)
    private String responseBody;
    @Column(name = "response_status_code")
    private Integer responseStatusCode;
    @Enumerated(EnumType.STRING)
    private NotificationProtocolErrorType errorType;

    public Long getWebhookNotificationProtocolId() {
        return webhookNotificationProtocolId;
    }

    public void setWebhookNotificationProtocolId(Long webhookNotificationProtocolId) {
        this.webhookNotificationProtocolId = webhookNotificationProtocolId;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public WebhookNotificationProtocolStatus getStatus() {
        return status;
    }

    public void setStatus(WebhookNotificationProtocolStatus status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getResponseReceivedAt() {
        return responseReceivedAt;
    }

    public void setResponseReceivedAt(LocalDateTime responseReceivedAt) {
        this.responseReceivedAt = responseReceivedAt;
    }

    public Long getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(Long webhookId) {
        this.webhookId = webhookId;
    }

    public Webhook getWebhook() {
        return webhook;
    }

    public void setWebhook(Webhook webhook) {
        this.webhook = webhook;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(Integer responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebhookNotificationHttpProtocol that = (WebhookNotificationHttpProtocol) o;
        return Objects.equals(webhookNotificationProtocolId, that.webhookNotificationProtocolId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(webhookNotificationProtocolId);
    }


    public NotificationProtocolErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(NotificationProtocolErrorType errorType) {
        this.errorType = errorType;
    }
}
