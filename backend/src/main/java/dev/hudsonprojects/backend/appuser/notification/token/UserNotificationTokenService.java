package dev.hudsonprojects.backend.appuser.notification.token;

import dev.hudsonprojects.backend.appuser.notification.token.entity.UserNotificationTokenEntity;
import dev.hudsonprojects.backend.appuser.notification.token.repository.UserNotificationTokenRepository;
import dev.hudsonprojects.backend.common.lib.RandomAlphaNumericalStringGenerator;
import dev.hudsonprojects.backend.security.refreshtoken.refreshtokenfamily.RefreshTokenFamily;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class UserNotificationTokenService {

    private final UserNotificationTokenRepository repository;

    public UserNotificationTokenService(UserNotificationTokenRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void invalidateByRefreshTokenFamilyId(Long refreshTokenFamilyId){
        if (refreshTokenFamilyId == null){
            return;
        }
        repository.invalidateByRefreshTokenFamilyId(refreshTokenFamilyId);
    }

    @Transactional
    public UserNotificationTokenEntity create(RefreshTokenFamily refreshTokenFamily) {
        if (refreshTokenFamily == null){
            return null;
        }
        UserNotificationTokenEntity notificationToken = new UserNotificationTokenEntity();
        notificationToken.setRefreshTokenFamilyId(refreshTokenFamily.getRefreshTokenFamilyId());
        notificationToken.setToken(RandomAlphaNumericalStringGenerator.generate(32));
        notificationToken.setValid(true);
        notificationToken.setRefreshTokenFamily(refreshTokenFamily);
        repository.save(notificationToken);
        return notificationToken;
    }
}
