package dev.hudsonprojects.api.course.registration;

import dev.hudsonprojects.api.course.Course;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CourseRegistrationDTO {

    @NotBlank
    private String name;
    @NotNull
    @Min(0)
    private Long slots;

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

    public Course toCourse() {
        Course course = new Course();
        course.setName(name);
        course.setSlots(slots);
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRegistrationDTO that = (CourseRegistrationDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(slots, that.slots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, slots);
    }

    @Override
    public String toString() {
        return "CourseRegistrationDTO{" +
                "name='" + name + '\'' +
                ", vacancies=" + slots +
                '}';
    }
}
