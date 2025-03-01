package dev.hudsonprojects.api.subscription;

import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.common.validation.constraint.cpf.CPF;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CreateSubscriptionDTO {

    @NotNull
    private Long courseId;
    @NotBlank
    @CPF
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
