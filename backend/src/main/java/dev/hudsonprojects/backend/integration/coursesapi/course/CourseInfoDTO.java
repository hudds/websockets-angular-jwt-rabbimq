package dev.hudsonprojects.backend.integration.coursesapi.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.SubscriptionStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseInfoDTO {

    private Long courseId;
    private String name;
    private Long slots;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long subscriptionCount;
    private Boolean subscribed;
    private LocalDateTime latestSubscription;
    private SubscriptionStatus.Status subscriptionStatus;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSlots() {
        return slots;
    }

    public void setSlots(Long slots) {
        this.slots = slots;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getSubscriptionCount() {
        return subscriptionCount;
    }

    public void setSubscriptionCount(Long subscriptionCount) {
        this.subscriptionCount = subscriptionCount;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public LocalDateTime getLatestSubscription() {
        return latestSubscription;
    }

    public void setLatestSubscription(LocalDateTime latestSubscription) {
        this.latestSubscription = latestSubscription;
    }

    public void setSubscriptionStatus(SubscriptionStatus.Status subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public SubscriptionStatus.Status getSubscriptionStatus() {
        return subscriptionStatus;
    }
}
