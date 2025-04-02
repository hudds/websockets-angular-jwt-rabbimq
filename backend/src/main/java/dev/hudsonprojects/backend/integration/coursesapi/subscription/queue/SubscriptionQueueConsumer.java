package dev.hudsonprojects.backend.integration.coursesapi.subscription.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.messagequeue.QueueConsumer;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionQueueConsumer extends QueueConsumer<CreateSubscriptionQueueMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionQueueConsumer.class);

    private final SubscriptionService subscriptionService;

    @Autowired
    protected SubscriptionQueueConsumer(ObjectMapper objectMapper, SubscriptionService subscriptionService) {
        super(objectMapper);
        this.subscriptionService = subscriptionService;
    }

    @Override
    @RabbitListener(queues = {SubscriptionQueueConfiguration.QUEUE_NAME})
    public void receive(@Payload String message){
        super.convertAndProcessMessage(message);
    }

    @Override
    protected Class<CreateSubscriptionQueueMessage> getMessageType() {
        return CreateSubscriptionQueueMessage.class;
    }

    @Override
    protected void processMessage(CreateSubscriptionQueueMessage message) {
        subscriptionService.subscribe(message);
    }

}
