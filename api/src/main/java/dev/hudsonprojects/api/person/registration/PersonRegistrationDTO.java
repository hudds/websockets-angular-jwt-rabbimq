package dev.hudsonprojects.api.person.registration;

import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.common.validation.constraint.cpf.CPF;
import dev.hudsonprojects.api.common.validation.constraint.notfuture.NotFuture;
import dev.hudsonprojects.api.person.Person;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Objects;

public class PersonRegistrationDTO {

    @Email
    private String email;
    @NotBlank
    private String name;
    @NotFuture
    private LocalDate birthDate;
    @NotBlank
    @CPF
    private String cpf;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = StringUtils.removeNonDigits(cpf);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRegistrationDTO that = (PersonRegistrationDTO) o;
        return Objects.equals(email, that.email) && Objects.equals(name, that.name) && Objects.equals(birthDate, that.birthDate) && Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, birthDate, cpf);
    }

    public Person toPerson(){
        Person person = new Person();
        person.setCpf(cpf);
        person.setBirthDate(birthDate);
        person.setName(name);
        person.setEmail(email);
        return person;
    }
}
