package dev.hudsonprojects.api.subscription;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import dev.hudsonprojects.api.course.Course;
import dev.hudsonprojects.api.person.Person;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "subscription", uniqueConstraints = @UniqueConstraint(columnNames = {"person_id", "course_id"}))
public class Subscription extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long subscriptionId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", updatable = false)
    private Person person;
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", updatable = false)
    private Course course;

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(subscriptionId, that.subscriptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(subscriptionId);
    }
}
