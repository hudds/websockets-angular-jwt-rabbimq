package dev.hudsonprojects.backend.integration.coursesapi.subscription.status;


public class SubscriptionStatusDTO {

    private SubscriptionStatus.Status status;
    private Long subscriptionId;
    private Long courseId;

    public SubscriptionStatusDTO(SubscriptionStatus subscriptionStatus) {
        subscriptionId = subscriptionStatus.getSubscriptionId();
        status = subscriptionStatus.getStatus();
        courseId = subscriptionStatus.getCourseId();
    }

    public SubscriptionStatus.Status getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus.Status status) {
        this.status = status;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
