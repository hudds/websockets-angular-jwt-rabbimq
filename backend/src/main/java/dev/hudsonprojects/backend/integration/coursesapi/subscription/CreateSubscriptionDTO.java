package dev.hudsonprojects.backend.integration.coursesapi.subscription;



import dev.hudsonprojects.backend.common.lib.util.StringUtils;

import java.util.Objects;

public class CreateSubscriptionDTO {

    private Long courseId;
    private String cpf;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCpf() {
        return StringUtils.removeNonDigits(cpf);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateSubscriptionDTO that = (CreateSubscriptionDTO) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, cpf);
    }

}
