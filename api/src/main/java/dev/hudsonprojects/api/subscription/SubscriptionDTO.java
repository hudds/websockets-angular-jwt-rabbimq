package dev.hudsonprojects.api.subscription;

import dev.hudsonprojects.api.course.CourseDTO;
import dev.hudsonprojects.api.person.PersonDTO;

public class SubscriptionDTO {

    private Long subscriptionId;
    private CourseDTO course;
    private PersonDTO person;

    public SubscriptionDTO(Subscription subscription) {
        this.subscriptionId = subscription.getSubscriptionId();
        this.course = subscription.getCourse() != null ? new CourseDTO(subscription.getCourse()) : null;
        this.person = subscription.getPerson() != null ? new PersonDTO(subscription.getPerson()) : null;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }
}
