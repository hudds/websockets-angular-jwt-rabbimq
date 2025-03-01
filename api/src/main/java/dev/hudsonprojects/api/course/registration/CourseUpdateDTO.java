package dev.hudsonprojects.api.course.registration;

import dev.hudsonprojects.api.course.Course;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CourseUpdateDTO {
    @NotNull
    private Long courseId;
    @NotBlank
    private String name;
    @NotNull
    @Min(0)
    private Long slots;

    public @NotNull Long getCourseId() {
        return courseId;
    }

    public void setCourseId(@NotNull Long courseId) {
        this.courseId = courseId;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotNull @Min(0) Long getSlots() {
        return slots;
    }

    public void setSlots(@NotNull @Min(0) Long slots) {
        this.slots = slots;
    }

    public Course toCourse() {
        Course course = new Course();
        copyValuesTo(course);
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseUpdateDTO that = (CourseUpdateDTO) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(name, that.name) && Objects.equals(slots, that.slots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, name, slots);
    }

    @Override
    public String toString() {
        return "CourseUpdateDTO{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", vacancies=" + slots +
                '}';
    }

    public void copyValuesTo(Course course) {
        course.setCourseId(courseId);
        course.setSlots(slots);
        course.setName(name);
    }
}
