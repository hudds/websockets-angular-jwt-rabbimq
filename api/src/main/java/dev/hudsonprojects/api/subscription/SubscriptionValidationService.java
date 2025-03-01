package dev.hudsonprojects.api.subscription;

import dev.hudsonprojects.api.common.messages.APIMessage;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.common.validation.GenericValidator;
import dev.hudsonprojects.api.course.Course;
import dev.hudsonprojects.api.course.repository.CourseRepository;
import dev.hudsonprojects.api.subscription.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionValidationService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionValidationService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<APIFieldError> validate(Subscription subscription) {
        List<APIFieldError> errors = getCourseValuesValidator().applyValidations(subscription);
        if(!errors.isEmpty()){
            return errors;
        }
        if(exists(subscription)){
            APIMessage message = new APIMessage("validation.subscription.exists");
            errors.add(new APIFieldError("person", message));
            errors.add(new APIFieldError("course", message));
        }
        long subscriptionCount = subscriptionRepository.countSubscriptionByCourseId(subscription.getCourse().getCourseId());
        long slots = subscription.getCourse().getSlots();
        long vacancies = slots - subscriptionCount;
        if(vacancies <= 0){
            APIMessage message = new APIMessage("validation.subscription.course.overbooking");
            errors.add(new APIFieldError("course", message));
        }
        return errors;
    }

    private boolean exists(Subscription subscription) {
        return subscriptionRepository.existsByCourseIdAndPersonId(subscription.getCourse().getCourseId(), subscription.getPerson().getPersonId());
    }


    private GenericValidator<Subscription> getCourseValuesValidator(){
        return GenericValidator.builder(Subscription.class)
                .addValidation(
                        "course",
                        "validation.subscription.course.required",
                        subscription -> subscription.getCourse() != null && subscription.getCourse().getCourseId() != null
                ).addValidation(
                        "person",
                        "validation.subscription.person.required",
                        subscription -> subscription.getPerson() != null && subscription.getPerson().getPersonId() != null
                ).build();
    }

}
