package dev.hudsonprojects.backend.common.sse;

import dev.hudsonprojects.backend.common.lib.lock.StripedLock;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

@Service
public class SseConnectionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SseConnectionManager.class);

    private final Map<String, Queue<SseEmitterStatusWrapper>> emitterMap = new ConcurrentHashMap<>();
    private final StripedLock stripedLock = new StripedLock(100);


    @Scheduled(fixedRate = 10000)
    public void heartbeat() {
        MediaType mediaType = MediaType.TEXT_PLAIN;
        String eventName = "HEARTBEAT";
        String eventData = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        broadcastMessage(eventName, eventData, mediaType);
    }

    public void broadcastMessage(String eventName, String eventData, MediaType mediaType) {
        Set<String> keys = new HashSet<>(emitterMap.keySet());
        for (String key : keys) {
            sendMessage(key, eventName, eventData, mediaType);
        }
    }

    public void registerEmitter(String key, SseEmitter emitter) {
        if (emitter == null || StringUtils.isBlank(key)) {
            return;
        }
        Lock lock = this.stripedLock.getLock(key);
        lock.lock();
        try {
            emitterMap.computeIfAbsent(key, k -> new ConcurrentLinkedQueue<>()).add(new SseEmitterStatusWrapper(emitter));
        } finally {
            lock.unlock();
        }
    }

    public void sendMessage(String key, String eventName, String message, MediaType mediaType) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        Queue<SseEmitterStatusWrapper> sseEmitters = emitterMap.get(key);
        if (sseEmitters == null) {
            return;
        }
        for (SseEmitterStatusWrapper emitter : sseEmitters) {
            try {
                emitter.getSseEmitter().send(SseEmitter.event()
                        .name(eventName)
                        .data(message, mediaType)
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
                emitter.completeWithError(e);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void clearClosed() {
        int totalRemoved = 0;
        int totalKeysRemoved = 0;
        Set<String> keys = new HashSet<>(emitterMap.keySet());
        for (String key : keys) {
            Queue<SseEmitterStatusWrapper> emitters = emitterMap.get(key);
            if (emitters != null) {
                int sizeBefore = emitters.size();
                emitters.removeIf(SseEmitterStatusWrapper::isClosed);
                totalRemoved += Math.min(sizeBefore - emitters.size(), 0);
                Lock lock = stripedLock.getLock(key);
                lock.lock();
                try {
                    if (emitterMap.get(key) != null && emitterMap.get(key).isEmpty()) {
                        emitterMap.remove(key);
                        totalKeysRemoved++;
                    }
                } finally {
                    lock.unlock();
                }
            }
            LOGGER.info("SSE connections removed: {}", totalRemoved);
            LOGGER.info("SSE keys removed: {}", totalKeysRemoved);
        }
    }

}