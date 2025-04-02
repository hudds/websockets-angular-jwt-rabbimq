package dev.hudsonprojects.backend.integration.coursesapi.subscription;


import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;

public class SubscriptionResponseDTO {

    private Long subscriptionId;
    private CourseInfoDTO course;

    public SubscriptionResponseDTO(SubscriptionDTO subscriptionDTO){
        this.subscriptionId = subscriptionDTO.getSubscriptionId();
        this.course = subscriptionDTO.getCourse();
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public CourseInfoDTO getCourse() {
        return course;
    }

    public void setCourse(CourseInfoDTO course) {
        this.course = course;
    }
}
