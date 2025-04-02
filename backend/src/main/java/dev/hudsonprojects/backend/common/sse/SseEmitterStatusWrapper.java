package dev.hudsonprojects.backend.common.sse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;
import java.util.UUID;

public class SseEmitterStatusWrapper {
    private static Logger LOGGER = LoggerFactory.getLogger(SseEmitterStatusWrapper.class);
    private final String id;
    private final SseEmitter sseEmitter;
    private boolean closed;

    public SseEmitterStatusWrapper(SseEmitter sseEmitter) {
        this.id = UUID.randomUUID().toString();
        this.sseEmitter = sseEmitter;
        sseEmitter.onTimeout(this::close);
        sseEmitter.onCompletion(this::close);
        sseEmitter.onError(e -> this.close());
    }

    public String getId() {
        return id;
    }

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        this.closed = true;
    }

    public SseEmitter getSseEmitter() {
        return sseEmitter;
    }

    public void complete() {
        this.closed = true;
        try {
            this.sseEmitter.complete();
        } catch (Exception e){
            LOGGER.warn("Failed to complete", e);
        }
    }

    public void completeWithError(Throwable e) {
        this.closed = true;
        try {
            this.sseEmitter.completeWithError(e);
        } catch (Exception eCompletion){
            LOGGER.warn("Failed to complete with error", e);
        }

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SseEmitterStatusWrapper that = (SseEmitterStatusWrapper) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
