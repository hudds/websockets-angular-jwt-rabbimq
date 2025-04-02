package dev.hudsonprojects.backend.integration.coursesapi.subscription.notification;

import dev.hudsonprojects.backend.appuser.notification.UserNotificationService;
import dev.hudsonprojects.backend.appuser.notification.UserNotificationTopicName;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.SubscriptionStatus;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionUserNotificationService {
    private final UserNotificationService userNotificationService;

    public SubscriptionUserNotificationService(UserNotificationService userNotificationService) {
        this.userNotificationService = userNotificationService;
    }

    public void sendStatus(SubscriptionStatus subscriptionStatus) {
        SubscriptionStatusNotificationDTO notification = new SubscriptionStatusNotificationDTO(subscriptionStatus);
        userNotificationService.sendNotification(subscriptionStatus.getUserId(), UserNotificationTopicName.SUBSCRIPTION_UPDATE, notification);
    }
}
