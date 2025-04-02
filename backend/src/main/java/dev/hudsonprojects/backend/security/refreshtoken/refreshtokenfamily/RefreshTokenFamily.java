package dev.hudsonprojects.backend.security.refreshtoken.refreshtokenfamily;

import java.time.LocalDateTime;

import dev.hudsonprojects.backend.appuser.notification.token.entity.UserNotificationTokenEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dev.hudsonprojects.backend.appuser.AppUser;

@Entity
@Table(name="refresh_token_family")
public class RefreshTokenFamily {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refresh_token_family_id")
	private Long refreshTokenFamilyId;
	@ManyToOne(optional = false)
	private AppUser appUser;
	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	private boolean invalidated;
	@OneToOne(mappedBy = "refreshTokenFamily", fetch = FetchType.LAZY)
	private UserNotificationTokenEntity userNotificationTokenEntity;
	
	public boolean isInvalidated() {
		return invalidated;
	}
	
	public boolean isNotInvalidated() {
		return !isInvalidated();
	}
	public void setInvalidated(boolean invalidated) {
		this.invalidated = invalidated;
	}
	public Long getRefreshTokenFamilyId() {
		return refreshTokenFamilyId;
	}
	public void setRefreshTokenFalilyId(Long refreshTokenFalilyId) {
		this.refreshTokenFamilyId = refreshTokenFalilyId;
	}

	public AppUser getAppUser() {
		return appUser;
	}
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
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

    public UserNotificationTokenEntity getUserNotificationTokenEntity() {
        return userNotificationTokenEntity;
    }

    public void setUserNotificationTokenEntity(UserNotificationTokenEntity userNotificationTokenEntity) {
        this.userNotificationTokenEntity = userNotificationTokenEntity;
    }
}
