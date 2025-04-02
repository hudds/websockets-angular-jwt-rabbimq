package dev.hudsonprojects.backend.integration.coursesapi.subscription.notification;

import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.SubscriptionStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class SubscriptionStatusNotificationDTO {

    private Long userId;
    private Long courseId;
    private Long subscriptionId;
    private SubscriptionStatus.Status status;
    private LocalDateTime date;

    public SubscriptionStatusNotificationDTO() {}

    public SubscriptionStatusNotificationDTO(SubscriptionStatus subscriptionStatus) {
        setSubscriptionId(subscriptionStatus.getSubscriptionId());
        setStatus(subscriptionStatus.getStatus());
        setDate(subscriptionStatus.getUpdatedAt() != null ? subscriptionStatus.getUpdatedAt() : subscriptionStatus.getCreatedAt());
        setUserId(subscriptionStatus.getUserId());
        setCourseId(subscriptionStatus.getCourseId());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public SubscriptionStatus.Status getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus.Status status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }



    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SubscriptionStatusNotificationDTO that = (SubscriptionStatusNotificationDTO) object;
        return Objects.equals(userId, that.userId) && Objects.equals(courseId, that.courseId) && Objects.equals(subscriptionId, that.subscriptionId) && status == that.status && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId, subscriptionId, status, date);
    }
}
