package dev.hudsonprojects.api.webhook;

import dev.hudsonprojects.api.webhook.entity.HttpRequestData;
import dev.hudsonprojects.api.webhook.entity.Webhook;
import dev.hudsonprojects.api.webhook.entity.WebhookTopic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateWebhookDTO {
    private String url;
    private String method;
    private List<HttpHeaderDTO> headers;
    private List<String> topics;
    private boolean active;

    public CreateWebhookDTO() {}

    public CreateWebhookDTO(Webhook webhook) {
        this.active = webhook.isActive();
        if (webhook.getTopics() != null){
            topics = new ArrayList<>(webhook.getTopics().stream().map(WebhookTopic::getTopic).toList());
        }
        HttpRequestData httpRequestData = webhook.getHttpRequestData();
        if(httpRequestData != null) {
            this.url = httpRequestData.getUrl();
            this.method = httpRequestData.getMethod();
            if(httpRequestData.getHeaders() != null) {
                this.headers = httpRequestData.getHeaders().stream()
                        .filter(Objects::nonNull)
                        .map(HttpHeaderDTO::new)
                        .toList();
            }
        }
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
        if(topics == null){
            topics = new ArrayList<>();
        }
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }


    public Webhook toWebhook() {
        Webhook webhook = new Webhook();
        webhook.setHttpRequestData(new HttpRequestData());
        copyValuesTo(webhook);
        return webhook;
    }

    public void copyValuesTo(Webhook webhook) {
        HttpRequestData httpRequestData = webhook.getHttpRequestData();
        webhook.setActive(active);
        if (topics != null) {
            List<WebhookTopic> webhookTopics = topics.stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .map(topic -> new WebhookTopic(webhook, topic))
                    .toList();
            webhook.setTopics(webhookTopics);
        }
        httpRequestData.setUrl(url);
        httpRequestData.setMethod(method);
        if (headers != null) {
            httpRequestData.setHeaders(headers.stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .map(dto -> dto.toHttpHeader(httpRequestData))
                    .toList());
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
