package dev.hudsonprojects.backend.security.refreshtoken.refreshtokenfamily;

import dev.hudsonprojects.backend.appuser.AppUser;
import dev.hudsonprojects.backend.appuser.notification.token.UserNotificationTokenService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenFamilyService {

    private final UserNotificationTokenService userNotificationTokenService;
    private final RefreshTokenFamilyRepository refreshTokenFamilyRepository;

    public RefreshTokenFamilyService(UserNotificationTokenService userNotificationTokenService, RefreshTokenFamilyRepository refreshTokenFamilyRepository) {
        this.userNotificationTokenService = userNotificationTokenService;
        this.refreshTokenFamilyRepository = refreshTokenFamilyRepository;
    }


    @Transactional
    public void invalidate(RefreshTokenFamily refreshTokenFamily) {
        refreshTokenFamily.setInvalidated(true);
        refreshTokenFamilyRepository.save(refreshTokenFamily);
        userNotificationTokenService.invalidateByRefreshTokenFamilyId(refreshTokenFamily.getRefreshTokenFamilyId());
    }

    @Transactional
    public RefreshTokenFamily create(AppUser appUser){
        RefreshTokenFamily refreshTokenFamily = new RefreshTokenFamily();
        refreshTokenFamily.setAppUser(appUser);
        refreshTokenFamilyRepository.save(refreshTokenFamily);
        userNotificationTokenService.create(refreshTokenFamily);
        return refreshTokenFamily;
    }
}
