package dev.hudsonprojects.api.webhook.event;

import java.util.Objects;

public class WebhookEvent {
    private final String topic;
    private final Object content;

    public WebhookEvent(String topic, Object content) {
        this.topic = Objects.requireNonNull(topic);
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }


    public Object getContent() {
        return content;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebhookEvent that = (WebhookEvent) o;
        return Objects.equals(topic, that.topic) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, content);
    }
}
