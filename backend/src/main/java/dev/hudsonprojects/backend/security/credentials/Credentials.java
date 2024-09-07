package dev.hudsonprojects.backend.security.credentials;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialsId;
    @Column(unique = true, nullable = false)
    private String identifier;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CredentialsType credentialsType;
    private String password;

    public Long getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Long credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public CredentialsType getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(credentialsId, that.credentialsId) && Objects.equals(identifier, that.identifier) && credentialsType == that.credentialsType && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credentialsId, identifier, credentialsType, password);
    }
}
