package dev.hudsonprojects.backend.security.notification;


import dev.hudsonprojects.backend.appuser.notification.token.UserNotificationTokenService;
import dev.hudsonprojects.backend.appuser.notification.token.repository.UserNotificationTokenRepository;
import dev.hudsonprojects.backend.common.exception.UnauthorizedException;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import org.springframework.stereotype.Service;

@Service
public class NotificationSecurityService {
    private final UserNotificationTokenRepository userNotificationTokenRepository;


    public NotificationSecurityService(UserNotificationTokenService userNotificationTokenService, UserNotificationTokenRepository userNotificationTokenRepository) {
        this.userNotificationTokenRepository = userNotificationTokenRepository;
    }

    public void validateToken(String token){
        if(token == null || !userNotificationTokenRepository.existsByTokenAndValidIsTrue(token)){
            throw new UnauthorizedException(ErrorDetailsBuilder.unauthorized().build());
        }
    }

    public void validateToken(String token, String username){
        if(token == null || username == null){
            throw new UnauthorizedException(ErrorDetailsBuilder.unauthorized().build());
        }
        boolean isValid = userNotificationTokenRepository.findUsernameByValidToken(token).map(tokenUsername -> tokenUsername.equals(username)).orElse(false);
        if(!isValid){
            throw new UnauthorizedException(ErrorDetailsBuilder.unauthorized().build());
        }
    }
}
