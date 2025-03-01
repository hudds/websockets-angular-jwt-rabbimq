package dev.hudsonprojects.api.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.api.messagequeue.exception.QueueConsumerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public abstract class QueueConsumer<T> {

    private final ObjectMapper objectMapper;

    @Autowired
    protected QueueConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public abstract void receive(@Payload String message);

    protected final void convertAndProcessMessage(String message) {
        try {
            T convertedMessage = objectMapper.reader().readValue(message, getMessageType());
            processMessage(convertedMessage);
        } catch (IOException e) {
            throw new QueueConsumerException(e);
        }
    }

    protected abstract Class<T> getMessageType();

    protected abstract void processMessage(T message);

}
