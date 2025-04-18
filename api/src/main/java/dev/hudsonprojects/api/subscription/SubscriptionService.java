package dev.hudsonprojects.api.subscription;

import dev.hudsonprojects.api.common.exception.APIInternalErrorException;
import dev.hudsonprojects.api.common.exception.APIMessageException;
import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.lib.RetryableExecutor;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.course.CourseService;
import dev.hudsonprojects.api.subscription.repository.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);
    private final RetryableExecutor retryableExecutor;
    private final SubscriptionProcessingService subscriptionProcessingService;

    @Autowired
    public SubscriptionService(SubscriptionProcessingService subscriptionProcessingService) {
        this.subscriptionProcessingService = subscriptionProcessingService;
        retryableExecutor = RetryableExecutor.builder().delayMillis(1000L)
                .maxAttempts(5)
                .delayPolicy(RetryableExecutor.DELAY_MULTIPLIED_BY_ATTEMPTS_POLICY)
                .supportedExceptions(CannotAcquireLockException.class)
                .build();
    }

    public SubscriptionDTO subscribe(CreateSubscriptionDTO subscriptionDTO) {
        try {
            return retryableExecutor.get(
                    () -> subscriptionProcessingService.subscribe(subscriptionDTO),
                    e -> LOGGER.warn("Failed to acquire lock while subscribing to course. Trying again.")
            );
        } catch (APIMessageException e) {
            throw e;
        } catch (Exception e) {
            throw new APIInternalErrorException(e);
        }
    }
}
