package dev.hudsonprojects.api.security.refreshtoken.refreshtokenfamily;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dev.hudsonprojects.api.appuser.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "public", name="refresh_token_family")
public class RefreshTokenFamily extends DefaultEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refresh_token_family_id")
	private Long refreshTokenFamilyId;
	@ManyToOne(optional = false)
	private AppUser appUser;
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
	
}
