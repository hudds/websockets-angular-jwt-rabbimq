package dev.hudsonprojects.api.appuser;

public class AppUserDTO {

	private String username;

	public AppUserDTO(AppUser appUser) {
		this.username = appUser.getUsername();
	}

	public String getUsername() {
		return username;
	}

}
