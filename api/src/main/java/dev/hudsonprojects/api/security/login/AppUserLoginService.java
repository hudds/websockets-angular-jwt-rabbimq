package dev.hudsonprojects.api.security.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import dev.hudsonprojects.api.appuser.AppUser;
import dev.hudsonprojects.api.security.JwtService;
import dev.hudsonprojects.api.security.refreshtoken.RefreshToken;
import dev.hudsonprojects.api.security.refreshtoken.RefreshTokenService;
import jakarta.transaction.Transactional;

@Service
public class AppUserLoginService {
	
	private final JwtService jwtService;
	private final AppUserDetailsService appUserDetailsService;
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenService refreshTokenService;
	
	
	public AppUserLoginService(JwtService jwtService, AppUserDetailsService appUserDetailsService,
			AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
		this.jwtService = jwtService;
		this.appUserDetailsService = appUserDetailsService;
		this.authenticationManager = authenticationManager;
		this.refreshTokenService = refreshTokenService;
	}


	@Transactional
	public SuccessfulLoginDTO authenticate(LoginDTO login) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		AppUser appUser = appUserDetailsService.loadAppUserByUsername(login.getUsername());
		String accessToken = jwtService.generateToken(new AppUserDetails(appUser));
		RefreshToken refreshToken = refreshTokenService.createRefreshTokenFamily(appUser);
		return new SuccessfulLoginDTO(appUser, refreshToken, accessToken);
	}


	

	
	
}
