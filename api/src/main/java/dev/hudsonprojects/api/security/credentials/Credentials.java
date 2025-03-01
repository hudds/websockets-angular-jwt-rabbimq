package dev.hudsonprojects.api.security.credentials;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table( schema = "public", name = "credentials")
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credentials_id")
    private Long credentialsId;
    @Column(name = "identifier", unique = true, nullable = false)
    private String identifier;
    @Column(name = "credentials_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CredentialsType credentialsType;
    @Column(name = "password")
    private String password;

    public Credentials() {
    }

    public Credentials(Credentials other) {
        credentialsId = other.credentialsId;
        identifier = other.identifier;
        credentialsType = other.credentialsType;
        password = other.password;
    }

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
