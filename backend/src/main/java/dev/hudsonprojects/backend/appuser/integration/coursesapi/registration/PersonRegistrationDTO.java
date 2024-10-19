package dev.hudsonprojects.backend.appuser.integration.coursesapi.registration;

import dev.hudsonprojects.backend.appuser.AppUser;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class PersonRegistrationDTO implements Serializable {

    private String email;
    private String name;
    private LocalDate birthDate;
    private String cpf;

    public PersonRegistrationDTO() {}

    public PersonRegistrationDTO(AppUser appUser){
        this.email = appUser.getEmail();
        this.cpf = appUser.getCpf();
        this.birthDate = appUser.getBirthDate();
        this.name = appUser.getName();
    }

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
        this.cpf = cpf;
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
}
