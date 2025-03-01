package dev.hudsonprojects.api.course;

import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

public class CourseDTO {

    private Long courseId;
    private String name;
    private Long slots;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CourseDTO(Course course) {
        this.courseId = course.getCourseId();
        this.name = course.getName();
        this.slots = course.getSlots();
        this.createdAt = course.getCreatedAt();
        this.updatedAt = course.getUpdatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

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


}
