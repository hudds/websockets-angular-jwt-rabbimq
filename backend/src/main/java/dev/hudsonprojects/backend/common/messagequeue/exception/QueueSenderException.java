package dev.hudsonprojects.backend.common.messagequeue.exception;

public class QueueSenderException extends RuntimeException {
    public QueueSenderException() {}

    public QueueSenderException(String message) {
        super(message);
    }

    public QueueSenderException(Throwable cause){
        super(cause);
    }

    public QueueSenderException(String message, Throwable cause){
        super(message, cause);
    }
}
