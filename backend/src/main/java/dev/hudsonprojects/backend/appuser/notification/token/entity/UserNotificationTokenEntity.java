package dev.hudsonprojects.backend.appuser.notification.token.entity;

import dev.hudsonprojects.backend.security.refreshtoken.refreshtokenfamily.RefreshTokenFamily;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_notification_token", schema = "public")
public class UserNotificationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNotificationTokenId;
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    @Column(name = "refresh_token_family_id", nullable = false, updatable = false)
    private Long refreshTokenFamilyId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_family_id", updatable = false, insertable = false)
    private RefreshTokenFamily refreshTokenFamily;
    @Column(name = "valid", nullable = false)
    private boolean valid;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getUserNotificationTokenId() {
        return userNotificationTokenId;
    }

    public void setUserNotificationTokenId(Long userNotificationTokenId) {
        this.userNotificationTokenId = userNotificationTokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getRefreshTokenFamilyId() {
        return refreshTokenFamilyId;
    }

    public void setRefreshTokenFamilyId(Long refreshTokenFamilyId) {
        this.refreshTokenFamilyId = refreshTokenFamilyId;
    }

    public RefreshTokenFamily getRefreshTokenFamily() {
        return refreshTokenFamily;
    }

    public void setRefreshTokenFamily(RefreshTokenFamily refreshTokenFamily) {
        this.refreshTokenFamily = refreshTokenFamily;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
