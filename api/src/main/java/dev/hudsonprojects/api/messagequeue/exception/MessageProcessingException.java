package dev.hudsonprojects.api.messagequeue.exception;

public class MessageProcessingException extends QueueConsumerException {

    public MessageProcessingException() {
    }

    public MessageProcessingException(String message) {
        super(message);
    }

    public MessageProcessingException(Throwable cause) {
        super(cause);
    }

    public MessageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
