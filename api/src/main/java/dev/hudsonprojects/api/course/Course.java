package dev.hudsonprojects.api.course;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "public", name = "course")
public class Course extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name="slots", nullable = false)
    private Long slots = 0L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(courseId);
    }
}
