package dev.hudsonprojects.api.subscription;

import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.course.CourseService;
import dev.hudsonprojects.api.subscription.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SubscriptionProcessingService {

    private final SubscriptionAdapter subscriptionAdapter;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionValidationService subscriptionValidationService;

    @Autowired
    public SubscriptionProcessingService(SubscriptionAdapter subscriptionAdapter, SubscriptionRepository subscriptionRepository, CourseService courseService, SubscriptionValidationService subscriptionValidationService) {
        this.subscriptionAdapter = subscriptionAdapter;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionValidationService = subscriptionValidationService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SubscriptionDTO subscribe(CreateSubscriptionDTO subscriptionDTO){
        Subscription subscription = subscriptionAdapter.createSubscription(subscriptionDTO);
        List<APIFieldError> errors = subscriptionValidationService.validate(subscription);

        if(!errors.isEmpty()){
            throw new ValidationException(ErrorDetailsBuilder.withMessage("validation.subscription.invalid")
                    .setFieldErrors(errors)
                    .build());
        }
        subscriptionRepository.save(subscription);
        return new SubscriptionDTO(subscription);
    }
}
