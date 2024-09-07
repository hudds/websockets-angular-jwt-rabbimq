package dev.hudsonprojects.backend.security.refreshtoken.refreshtokenfamily;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.security.refreshtoken.RefreshToken;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
	
	public boolean isInvalidated() {
		return invalidated;
	}
	
	public boolean isNotInvalidated() {
		return !isInvalidated();
	}
	public void setInvalidated(boolean invalidated) {
		this.invalidated = invalidated;
	}
	public Long getRefreshTokenFalilyId() {
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
	
	
	
}
