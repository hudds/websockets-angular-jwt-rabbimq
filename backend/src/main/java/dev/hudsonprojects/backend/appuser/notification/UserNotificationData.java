package dev.hudsonprojects.backend.appuser.notification;

import java.util.Objects;

public class UserNotificationData {

    private Long userId;
    private UserNotificationTopicName topic;
    private Object content;

    public UserNotificationData() {
    }

    private UserNotificationData(Builder builder){
        this.userId = builder.userId;
        this.topic = builder.eventName;
        this.content = builder.message;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }


    public void setTopic(UserNotificationTopicName topic) {
        this.topic = topic;
    }

    public UserNotificationTopicName getTopic() {
        return topic;
    }


    public void setContent(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public UserNotificationMessage getMessage(){
        return new UserNotificationMessage(topic, content);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserNotificationData that = (UserNotificationData) object;
        return Objects.equals(userId, that.userId) && topic == that.topic && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, topic, content);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private UserNotificationTopicName eventName;
        private Object message;

        private Builder(){}

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setEventName(UserNotificationTopicName eventName) {
            this.eventName = eventName;
            return this;
        }

        public Builder setMessage(Object message) {
            this.message = message;
            return this;
        }

        public UserNotificationData build(){
            return new UserNotificationData(this);
        }
    }
}
