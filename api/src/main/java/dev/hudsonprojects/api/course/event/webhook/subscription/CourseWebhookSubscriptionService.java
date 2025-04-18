package dev.hudsonprojects.api.course.event.webhook.subscription;

import dev.hudsonprojects.api.common.exception.APIInternalErrorException;
import dev.hudsonprojects.api.common.exception.APIMessageException;
import dev.hudsonprojects.api.common.lib.RetryableExecutor;
import dev.hudsonprojects.api.webhook.WebhookDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseWebhookSubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseWebhookSubscriptionService.class);

    private final CourseWebhookSubscriptionProcessingService courseWebhookSubscriptionProcessingService;
    private final RetryableExecutor retryableExecutor;

    @Autowired
    public CourseWebhookSubscriptionService(CourseWebhookSubscriptionProcessingService courseWebhookSubscriptionProcessingService) {
        this.courseWebhookSubscriptionProcessingService = courseWebhookSubscriptionProcessingService;
        retryableExecutor = RetryableExecutor.builder().delayMillis(1000L)
                .maxAttempts(3)
                .delayPolicy(RetryableExecutor.DELAY_MULTIPLIED_BY_ATTEMPTS_POLICY)
                .supportedExceptions(CannotAcquireLockException.class)
                .build();
    }

    public WebhookDTO subscribe(CourseWebhookSubscriptionDTO courseWebhookSubscriptionDTO) {
        try {
            return retryableExecutor.get(
                    () -> courseWebhookSubscriptionProcessingService.subscribe(courseWebhookSubscriptionDTO),
                    e -> LOGGER.warn("Failed to acquire lock during webhook subscription. Trying again.")
            );
        } catch (APIMessageException e){
            throw e;
        } catch (Exception e){
            throw new APIInternalErrorException(e);
        }
    }

    public Optional<WebhookDTO> unsubscribe(CourseWebhookSubscriptionDTO courseWebhookSubscriptionDTO) {
        try {
            return retryableExecutor.get(
                    () -> courseWebhookSubscriptionProcessingService.unsubscribe(courseWebhookSubscriptionDTO),
                    e -> LOGGER.warn("Failed to acquire lock while unsubscribing to webhook. Trying again.")
            );
        } catch (APIMessageException e){
            throw e;
        } catch (Exception e){
            throw new APIInternalErrorException(e);
        }
    }


}
