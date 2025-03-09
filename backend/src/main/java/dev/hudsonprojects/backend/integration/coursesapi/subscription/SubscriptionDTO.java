package dev.hudsonprojects.backend.integration.coursesapi.subscription;


import dev.hudsonprojects.backend.appuser.integration.coursesapi.PersonDTO;
import dev.hudsonprojects.backend.integration.coursesapi.course.CourseInfoDTO;

public class SubscriptionDTO {

    private Long subscriptionId;
    private CourseInfoDTO course;
    private PersonDTO person;


    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public CourseInfoDTO getCourse() {
        return course;
    }

    public void setCourse(CourseInfoDTO course) {
        this.course = course;
    }
}
