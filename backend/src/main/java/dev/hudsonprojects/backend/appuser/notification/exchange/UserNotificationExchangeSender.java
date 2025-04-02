package dev.hudsonprojects.backend.appuser.notification.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.appuser.notification.UserNotificationData;
import dev.hudsonprojects.backend.common.messagequeue.exception.QueueSenderException;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationExchangeSender {

    private final RabbitTemplate template;
    private final FanoutExchange fanout;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserNotificationExchangeSender(RabbitTemplate template, @Qualifier(UserNotificationExchangeConfiguration.EXCHANGE_QUALIFIER_NAME) FanoutExchange fanout, ObjectMapper objectMapper) {
        this.template = template;
        this.fanout = fanout;
        this.objectMapper = objectMapper;
    }

    public void send(UserNotificationData message) {
        try {
            template.convertAndSend(fanout.getName(), "", objectMapper.writeValueAsString(message));
        }catch (Exception e){
            throw new QueueSenderException(e);
        }

    }
}
