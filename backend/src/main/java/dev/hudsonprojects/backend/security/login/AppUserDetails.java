package dev.hudsonprojects.backend.security.login;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.security.credentials.CredentialsType;

public class AppUserDetails implements UserDetails{
	
    private final Long userId;
    private final String externalId;
    private final String password;
    private final CredentialsType credentialsType;
    
    public AppUserDetails(AppUser appUser) {
    	this.userId = appUser.getUserId();
    	this.externalId = appUser.getUsername();
    	this.password = appUser.getCredentials().getPassword();
    	this.credentialsType = appUser.getCredentials().getCredentialsType();
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(new SimpleGrantedAuthority(String.valueOf(this.credentialsType)));
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.externalId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	
	public CredentialsType getCredentialsType() {
		return credentialsType;
	}
	

}
