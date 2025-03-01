package dev.hudsonprojects.api.appuser;

import java.util.Objects;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import jakarta.persistence.*;

import dev.hudsonprojects.api.security.credentials.Credentials;

@Entity
@Table(schema = "public", name = "app_user")
public class AppUser extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username", unique = true, nullable = false, updatable = false)
    private String username;
    @OneToOne
    @JoinColumn(name = "credentials_id", referencedColumnName = "credentials_id")
    private Credentials credentials;

    public AppUser() {}

    public AppUser(AppUser other) {
        super(other);
        this.userId = other.userId;
        this.username = other.username;
        this.credentials = other.credentials != null ? new Credentials(other.credentials) : null;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
    
	public String getUsername() {
		return username;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(userId, appUser.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
