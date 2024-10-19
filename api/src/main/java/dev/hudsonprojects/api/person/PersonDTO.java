package dev.hudsonprojects.api.person;

import java.time.LocalDate;

public class PersonDTO {

    private Long personId;
    private String email;
    private String name;
    private LocalDate birthDate;
    private String cpf;

    public PersonDTO(Person person) {
        this.personId = person.getPersonId();
        this.email = person.getEmail();
        this.name = person.getName();
        this.birthDate = person.getBirthDate();
        this.cpf = person.getCpf();
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
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
}
