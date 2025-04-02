package dev.hudsonprojects.backend.security.login;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.security.refreshtoken.RefreshToken;

public class SuccessfulLoginDTO {
	
	private AppUserDTO user;
	private String accessToken;
	private String refreshToken;
	private String userNotificationToken;
	
	
	public SuccessfulLoginDTO(AppUser appUser, RefreshToken refreshToken, String accessToken, String userNotificationToken) {
		this.user = new AppUserDTO(appUser);
		this.accessToken = accessToken;
		this.refreshToken = refreshToken.getToken();
		this.userNotificationToken = userNotificationToken;
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

    public String getUserNotificationToken() {
        return userNotificationToken;
    }

    public void setUserNotificationToken(String userNotificationToken) {
        this.userNotificationToken = userNotificationToken;
    }
}
