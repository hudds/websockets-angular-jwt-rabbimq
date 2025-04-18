package dev.hudsonprojects.api.course;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseInfoDTO extends CourseDTO {

    private Long subscriptionCount;
    private Boolean subscribed;
    private LocalDateTime latestSubscription;

    public CourseInfoDTO(Course course) {
        super(course);
    }

    public CourseInfoDTO(Course course, Long subscriptionCount, LocalDateTime latestSubscription) {
        super(course);
        this.subscriptionCount = subscriptionCount;
        this.latestSubscription = latestSubscription;
    }

    public CourseInfoDTO(Course course, Long subscriptionCount, LocalDateTime latestSubscription,  Boolean subscribed) {
        super(course);
        this.subscriptionCount = subscriptionCount;
        this.latestSubscription = latestSubscription;
        this.subscribed = subscribed;
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
}
