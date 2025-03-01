package dev.hudsonprojects.api.messagequeue.exception;

public class QueueConsumerException extends RuntimeException {
    public QueueConsumerException() {}

    public QueueConsumerException(String message) {
        super(message);
    }

    public QueueConsumerException(Throwable cause){
        super(cause);
    }

    public QueueConsumerException(String message, Throwable cause){
        super(message, cause);
    }
}
