package dev.hudsonprojects.backend.appuser.notification;

import dev.hudsonprojects.backend.appuser.AppUserRepository;
import dev.hudsonprojects.backend.appuser.notification.exchange.UserNotificationExchangeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
        if(userId == null){
            return;
        }
        sender.send(UserNotificationData.builder()
                .setUserId(userId)
                .setMessage(message)
                .setEventName(eventName)
                .build());
    }

    public void sendGlobalNotification(UserNotificationTopicName eventName, Object message) {
        sendNotification(null, eventName, message);
    }

    public void sendNotificationToUserByCPF(String cpf, UserNotificationTopicName eventName, Object message) {
        Long userId = appUserRepository.findUserIdByCpf(cpf).orElse(null);
        if (userId == null) {
            return;
        }
        sendNotification(userId, eventName, message);
    }

    public void sendNotification(UserNotificationData userNotification) {
        String destination = "/" + getDestination(userNotification);
        messagingTemplate.convertAndSend(destination, userNotification.getMessage());
    }

    private static String getDestination(UserNotificationData userNotification){
        return userNotification.getUserId() == null ? "global" : getUserKey(userNotification.getUserId());
    }

    private static String getUserKey(Long userId) {
        return "user/id/" + userId;
    }
}
