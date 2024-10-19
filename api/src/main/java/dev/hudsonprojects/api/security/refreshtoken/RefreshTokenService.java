package dev.hudsonprojects.api.security.refreshtoken;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import dev.hudsonprojects.api.appuser.AppUser;
import dev.hudsonprojects.api.common.exception.UnauthorizedException;
import dev.hudsonprojects.api.common.lib.RandomAlphaNumericalStringGenerator;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.security.JwtService;
import dev.hudsonprojects.api.security.login.AppUserDetails;
import dev.hudsonprojects.api.security.login.SuccessfulLoginDTO;
import dev.hudsonprojects.api.security.refreshtoken.refreshtokenfamily.RefreshTokenFamily;
import dev.hudsonprojects.api.security.refreshtoken.refreshtokenfamily.RefreshTokenFamilyRepository;
import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	private final RefreshTokenFamilyRepository refreshTokenFamilyRepository;
	private final JwtService jwtService;
	
	

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
			RefreshTokenFamilyRepository refreshTokenFamilyRepository, JwtService jwtService) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.refreshTokenFamilyRepository = refreshTokenFamilyRepository;
		this.jwtService = jwtService;
	}

	@Transactional
	public RefreshToken createRefreshTokenFamily(AppUser appUser) {
		RefreshTokenFamily refreshTokenFamily = new RefreshTokenFamily();
		refreshTokenFamily.setAppUser(appUser);
		refreshTokenFamilyRepository.save(refreshTokenFamily);
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setRefreshTokenFamily(refreshTokenFamily);
		refreshToken.setToken(generateRandomToken());
		refreshTokenRepository.save(refreshToken);
		
		
		return refreshToken;
	}


	@Transactional
	public SuccessfulLoginDTO refreshToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
				.filter(rToken -> !rToken.getRefreshTokenFamily().isInvalidated())
				.orElseThrow(() -> new UnauthorizedException(ErrorDetailsBuilder.buildWithMessage("authentication.refreshtoken.invalid")));
		
		if(refreshToken.isInvalidated()) {
			refreshToken.getRefreshTokenFamily().setInvalidated(true);
			refreshTokenFamilyRepository.save(refreshToken.getRefreshTokenFamily());
			return null;
		}
		RefreshToken newRefreshToken = new RefreshToken();
		newRefreshToken.setRefreshTokenFamily(refreshToken.getRefreshTokenFamily());
		newRefreshToken.setToken(generateRandomToken());
		refreshToken.setInvalidated(true);
		refreshTokenRepository.save(newRefreshToken);
		refreshTokenRepository.save(refreshToken);
		AppUser appUser = newRefreshToken.getAppUser();
		String accessToken = jwtService.generateToken(new AppUserDetails(appUser));
		return new SuccessfulLoginDTO(appUser, newRefreshToken, accessToken);
	}
	
	
	private String generateRandomToken() {
		return RandomAlphaNumericalStringGenerator.generate(32);
	}
	
	
	@Transactional
	public void revokeToken(String token) {
		refreshTokenRepository.findByToken(token)
			.filter(RefreshToken::isNotInvalidated)
			.map(RefreshToken::getRefreshTokenFamily)
			.filter(RefreshTokenFamily::isNotInvalidated)
			.ifPresent(tokenFamily -> {
				tokenFamily.setInvalidated(true);
				this.refreshTokenFamilyRepository.save(tokenFamily);
			});
	}
}
