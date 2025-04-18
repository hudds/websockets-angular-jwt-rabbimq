package dev.hudsonprojects.backend.appuser.notification;

import dev.hudsonprojects.backend.appuser.AppUserRepository;
import dev.hudsonprojects.backend.appuser.notification.exchange.UserNotificationExchangeSender;
import dev.hudsonprojects.backend.common.config.WebsocketConfig;
import dev.hudsonprojects.backend.common.exception.NotFoundException;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserNotificationService {

    private final AppUserRepository appUserRepository;
    private final UserNotificationExchangeSender sender;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public UserNotificationService(AppUserRepository appUserRepository, UserNotificationExchangeSender sender, SimpMessagingTemplate messagingTemplate) {
        this.appUserRepository = appUserRepository;
        this.sender = sender;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(Long userId, UserNotificationTopicName eventName, Object message) {
        String username = null;
        if(userId != null) {
            username = appUserRepository.findUsernameByUserId(userId)
                    .orElseThrow(() -> new NotFoundException(ErrorDetailsBuilder.buildWithMessage("error.user.notFound")));
        }
        sendNotification(username, eventName, message);
    }

    public void sendNotification(String username, UserNotificationTopicName eventName, Object message) {
        sender.send(UserNotificationData.builder()
                .setUsername(username)
                .setMessage(message)
                .setEventName(eventName)
                .build());
    }

    public void sendGlobalNotification(UserNotificationTopicName eventName, Object message) {
        sendNotification((String) null, eventName, message);
    }

    public void sendNotificationToUserByCPF(String cpf, UserNotificationTopicName eventName, Object message) {
        String username = appUserRepository.findUsernameByCpf(cpf).orElse(null);
        if (username == null) {
            return;
        }
        sendNotification(username, eventName, message);
    }

    public void sendNotification(UserNotificationData userNotification) {
        String destination = getDestination(userNotification);
        messagingTemplate.convertAndSend(destination, userNotification.getMessage());
    }

    private static String getDestination(UserNotificationData userNotification){
        return userNotification.getUsername() == null ? "/notification/global" : getUserKey(userNotification.getUsername());
    }

    private static String getUserKey(String username) {
        return WebsocketConfig.NOTIFICATION_USER_DESTINATION + username;
    }
}
