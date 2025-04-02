package dev.hudsonprojects.backend.appuser.notification;


import java.util.Objects;

public class UserNotificationMessage {

    private final UserNotificationTopicName topic;
    private final Object content;

    public UserNotificationMessage(UserNotificationTopicName topic, Object content) {
        this.topic = topic;
        this.content = content;
    }

    public UserNotificationTopicName getTopic() {
        return topic;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserNotificationMessage that = (UserNotificationMessage) object;
        return topic == that.topic && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, content);
    }
}
