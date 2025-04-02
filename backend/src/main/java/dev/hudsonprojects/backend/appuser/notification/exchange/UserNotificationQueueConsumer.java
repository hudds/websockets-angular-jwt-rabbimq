package dev.hudsonprojects.backend.appuser.notification.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.appuser.notification.UserNotificationData;
import dev.hudsonprojects.backend.appuser.notification.UserNotificationService;
import dev.hudsonprojects.backend.common.messagequeue.QueueConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationQueueConsumer extends QueueConsumer<UserNotificationData> {

    private static Logger LOGGER = LoggerFactory.getLogger(UserNotificationQueueConsumer.class);

    private final UserNotificationService userNotificationService;

    @Autowired
    public UserNotificationQueueConsumer(ObjectMapper objectMapper, UserNotificationService userNotificationService) {
        super(objectMapper);
        this.userNotificationService = userNotificationService;
    }

    @Override
    @RabbitListener(queues = "#{"+ UserNotificationExchangeConfiguration.QUEUE_QUALIFIER_NAME+".name}")
    public void receive(String message) {
        super.convertAndProcessMessage(message);
    }

    @Override
    protected Class<UserNotificationData> getMessageType() {
        return UserNotificationData.class;
    }

    @Override
    protected void processMessage(UserNotificationData message) {
        userNotificationService.sendNotification(message);
    }
}
