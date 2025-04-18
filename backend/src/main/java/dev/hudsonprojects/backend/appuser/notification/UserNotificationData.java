package dev.hudsonprojects.backend.appuser.notification;

import java.util.Objects;

public class UserNotificationData {

    private String username;
    private UserNotificationTopicName topic;
    private Object content;

    public UserNotificationData() {
    }

    private UserNotificationData(Builder builder){
        this.username = builder.username;
        this.topic = builder.eventName;
        this.content = builder.message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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
        return Objects.equals(username, that.username) && topic == that.topic && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, topic, content);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private String username;
        private UserNotificationTopicName eventName;
        private Object message;

        private Builder(){}

        public Builder setUsername(String username) {
            this.username = username;
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
