package dev.hudsonprojects.api.webhook.notification;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class WebhookNotification {

    private String url;
    private String method;
    private Map<String, String> headers;
    private Object body;
    private String contentType;
    private Set<Long> webhookIds = new HashSet<>();

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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        WebhookNotification that = (WebhookNotification) object;
        return Objects.equals(url, that.url) && Objects.equals(method, that.method) && Objects.equals(headers, that.headers) && Objects.equals(body, that.body) && Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method, headers, body, contentType);
    }

    public Set<Long> getWebhookIds() {
        return webhookIds;
    }

    public void setWebhookIds(Set<Long> webhookIds) {
        this.webhookIds = webhookIds;
    }

    public void addWebhookId(Long webhookId) {
        if(webhookIds == null) {
            webhookIds = new HashSet<>();
        }
        webhookIds.add(webhookId);
    }
}
