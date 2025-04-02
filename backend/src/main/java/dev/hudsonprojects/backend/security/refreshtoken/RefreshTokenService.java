package dev.hudsonprojects.backend.security.refreshtoken;

import dev.hudsonprojects.backend.security.refreshtoken.refreshtokenfamily.RefreshTokenFamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.common.exception.UnauthorizedException;
import dev.hudsonprojects.backend.common.lib.RandomAlphaNumericalStringGenerator;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.security.JwtService;
import dev.hudsonprojects.backend.security.login.AppUserDetails;
import dev.hudsonprojects.backend.security.login.SuccessfulLoginDTO;
import dev.hudsonprojects.backend.security.refreshtoken.refreshtokenfamily.RefreshTokenFamily;
import dev.hudsonprojects.backend.security.refreshtoken.refreshtokenfamily.RefreshTokenFamilyRepository;
import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenService.class);
	private final RefreshTokenRepository refreshTokenRepository;
	private final RefreshTokenFamilyRepository refreshTokenFamilyRepository;
	private final JwtService jwtService;
	private final RefreshTokenFamilyService refreshTokenFamilyService;
	
	

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               RefreshTokenFamilyRepository refreshTokenFamilyRepository, JwtService jwtService, RefreshTokenFamilyService refreshTokenFamilyService) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.refreshTokenFamilyRepository = refreshTokenFamilyRepository;
		this.jwtService = jwtService;
        this.refreshTokenFamilyService = refreshTokenFamilyService;
    }

	@Transactional
	public RefreshToken createRefreshTokenFamily(AppUser appUser) {

		RefreshTokenFamily refreshTokenFamily = refreshTokenFamilyService.create(appUser);
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
			refreshTokenFamilyService.invalidate(refreshToken.getRefreshTokenFamily());
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
		return new SuccessfulLoginDTO(appUser, newRefreshToken, accessToken, refreshToken.getRefreshTokenFamily().getUserNotificationTokenEntity().getToken());
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
			.ifPresent(refreshTokenFamilyService::invalidate);
	}
}
