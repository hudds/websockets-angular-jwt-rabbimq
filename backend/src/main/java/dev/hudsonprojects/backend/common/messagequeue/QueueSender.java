package dev.hudsonprojects.backend.common.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.messagequeue.exception.QueueSenderException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public abstract class QueueSender<T> {

    protected final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    protected QueueSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(T object) {
        try {
            String json = objectMapper.writer().writeValueAsString(object);
            rabbitTemplate.convertAndSend(getQueueName(), json);
        } catch (JsonProcessingException e) {
            throw new QueueSenderException(e);
        }
    }

    @Async
    public void sendAsync(T object){
        send(object);
    }

    protected abstract String getQueueName();

}
