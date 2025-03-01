package dev.hudsonprojects.api.security.refreshtoken;

import java.time.LocalDateTime;

import dev.hudsonprojects.api.common.entity.DefaultEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dev.hudsonprojects.api.appuser.AppUser;
import dev.hudsonprojects.api.security.refreshtoken.refreshtokenfamily.RefreshTokenFamily;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "public", name = "refresh_token")
public class RefreshToken extends DefaultEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refresh_token")
	private Long refreshTokenId;
	@Column(name = "token", length = 32, unique = true, nullable = false, updatable = false)
	private String token;
	@ManyToOne
	@JoinColumn(name = "refresh_token_family_id", referencedColumnName = "refresh_token_family_id")
	private RefreshTokenFamily refreshTokenFamily;
	private boolean invalidated;

	public Long getRefreshTokenId() {
		return refreshTokenId;
	}

	public void setRefreshTokenId(Long refreshTokenId) {
		this.refreshTokenId = refreshTokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RefreshTokenFamily getRefreshTokenFamily() {
		return refreshTokenFamily;
	}

	public void setRefreshTokenFamily(RefreshTokenFamily refreshTokenFamily) {
		this.refreshTokenFamily = refreshTokenFamily;
	}

	public boolean isInvalidated() {
		return invalidated;
	}
	
	public boolean isNotInvalidated() {
		return !isInvalidated();
	}

	public void setInvalidated(boolean invalidated) {
		this.invalidated = invalidated;
	}

	public AppUser getAppUser() {
		return getRefreshTokenFamily() != null ? getRefreshTokenFamily().getAppUser() : null;
	}

}
