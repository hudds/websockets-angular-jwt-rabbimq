package dev.hudsonprojects.api.security.login;

import dev.hudsonprojects.api.appuser.AppUser;
import dev.hudsonprojects.api.appuser.AppUserDTO;
import dev.hudsonprojects.api.security.refreshtoken.RefreshToken;

public class SuccessfulLoginDTO {
	
	private AppUserDTO user;
	private String accessToken;
	private String refreshToken;
	
	
	public SuccessfulLoginDTO(AppUser appUser, RefreshToken refreshToken, String accessToken) {
		this.user = new AppUserDTO(appUser);
		this.accessToken = refreshToken.getToken();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken.getToken();
	}
	
	public AppUserDTO getUser() {
		return user;
	}
	public void setUser(AppUserDTO user) {
		this.user = user;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
}
