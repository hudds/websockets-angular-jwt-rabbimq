package dev.hudsonprojects.backend.integration.coursesapi.subscription.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.messagequeue.QueueSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionQueueSender extends QueueSender<CreateSubscriptionQueueMessage> {


    @Autowired
    public SubscriptionQueueSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        super(rabbitTemplate, objectMapper);
    }


    @Override
    protected String getQueueName() {
        return SubscriptionQueueConfiguration.QUEUE_NAME;
    }

}
