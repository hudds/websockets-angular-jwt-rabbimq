package dev.hudsonprojects.api.person;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import dev.hudsonprojects.api.common.lib.util.StringUtils;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(schema = "public", name = "person")
public class Person extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;
    @Column(name = "email", unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(nullable = false, unique = true)
    private String cpf;

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
        this.cpf = StringUtils.removeNonDigits(cpf);
    }
}
