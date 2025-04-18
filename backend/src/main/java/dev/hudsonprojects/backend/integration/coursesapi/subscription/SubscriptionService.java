package dev.hudsonprojects.backend.integration.coursesapi.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.appuser.AppUserService;
import dev.hudsonprojects.backend.common.exception.APIErrorType;
import dev.hudsonprojects.backend.common.exception.InternalErrorException;
import dev.hudsonprojects.backend.common.exception.ValidationException;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import dev.hudsonprojects.backend.common.messages.APIMessage;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.integration.coursesapi.error.ErrorDetailsAdapter;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.person.registration.PersonRegistrationCoursesAPIService;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.notification.SubscriptionUserNotificationService;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.queue.CreateSubscriptionQueueMessage;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.queue.SubscriptionQueueSender;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.SubscriptionStatus;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.SubscriptionStatusDTO;
import dev.hudsonprojects.backend.integration.coursesapi.subscription.status.SubscriptionStatusService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    private static final String VALIDATION_SUBSCRIPTION_EXISTS = "validation.subscription.exists";
    private final SubscriptionAPIClient subscriptionAPIClient;
    private final AppUserService appUserService;
    private final ObjectMapper objectMapper;
    private final PersonRegistrationCoursesAPIService personRegistrationCoursesAPIService;
    private final SubscriptionStatusService subscriptionStatusService;
    private final SubscriptionQueueSender queue;
    private final SubscriptionUserNotificationService subscriptionUserNotificationService;

    @Autowired
    public SubscriptionService(SubscriptionAPIClient subscriptionAPIClient, AppUserService appUserService, ObjectMapper objectMapper, PersonRegistrationCoursesAPIService personRegistrationCoursesAPIService, SubscriptionStatusService subscriptionStatusService, SubscriptionQueueSender queue, SubscriptionUserNotificationService subscriptionUserNotificationService) {
        this.subscriptionAPIClient = subscriptionAPIClient;
        this.appUserService = appUserService;
        this.objectMapper = objectMapper;
        this.personRegistrationCoursesAPIService = personRegistrationCoursesAPIService;
        this.subscriptionStatusService = subscriptionStatusService;
        this.queue = queue;
        this.subscriptionUserNotificationService = subscriptionUserNotificationService;
    }


    @Transactional
    public SubscriptionStatusDTO subscribe(Long courseId) {
        if(courseId == null){
            throw new ValidationException(ErrorDetailsBuilder.withMessage("validation.subscription.course.required").build());
        }
        AppUserDTO loggedUser = appUserService.getLoggedUser();
        SubscriptionStatus subscriptionStatus = subscriptionStatusService.updateStatus(loggedUser.getUserId(), courseId, SubscriptionStatus.Status.PENDING);
        CreateSubscriptionDTO createSubscription = new CreateSubscriptionDTO();
        createSubscription.setCpf(loggedUser.getCpf());
        createSubscription.setCourseId(courseId);
        queue.sendAsync(new CreateSubscriptionQueueMessage(loggedUser.getUserId(), createSubscription));
        return new SubscriptionStatusDTO(subscriptionStatus);
    }

    private Optional<ErrorDetails> translateAPIMessage(Integer statusCode, String responseBody) {
        if(statusCode == null || StringUtils.isBlank(responseBody) || (statusCode != 400 && statusCode != 404)){
            return Optional.empty();
        }
        ErrorDetailsAdapter errorDetailsAdapter = new ErrorDetailsAdapter(objectMapper, Set.of(VALIDATION_SUBSCRIPTION_EXISTS, "validation.subscription.course.overbooking"));
        return errorDetailsAdapter.adapt(APIErrorType.VALIDATION_ERROR, responseBody);
    }

    public void subscribe(CreateSubscriptionQueueMessage message) {
        try {
            String cpf = message.getCreateSubscriptionDTO().getCpf();
            integrateUser(cpf);
            SubscriptionDTO subscription = subscriptionAPIClient.subscribe(message.getCreateSubscriptionDTO());
            SubscriptionStatus subscriptionStatus = subscriptionStatusService.updateStatus(message.getUserId(), SubscriptionStatus.Status.SUCCESS, subscription);
            sendSubscriptionStatus(subscriptionStatus);
        } catch (CoursesAPIHttpException e) {
            Optional<ErrorDetails> errorDetails = translateAPIMessage(e.getStatusCode(), e.getResponseBody());
            if (errorDetails.isPresent()) {
                updateSubscriptionStatus(message, errorDetails.get());
                throw new ValidationException(errorDetails.get());
            }
            SubscriptionStatus subscriptionStatus = subscriptionStatusService.updateStatus(message.getUserId(), message.getCreateSubscriptionDTO().getCourseId(), SubscriptionStatus.Status.ERROR);
            sendSubscriptionStatus(subscriptionStatus);
            throw new InternalErrorException(ErrorDetailsBuilder.withMessage("error.subscription").build(), e);
        }
    }

    private void sendSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        try {
            subscriptionUserNotificationService.sendStatus(subscriptionStatus);
        } catch (Exception e) {
            LOGGER.error("Failed to send notification about subscription status", e);
        }
    }

    private void integrateUser(String cpf) {
        try {
            personRegistrationCoursesAPIService.registerUserByCpfIfNotRegistered(cpf);
        } catch (CoursesAPIHttpException e){
            throw new InternalErrorException(ErrorDetailsBuilder.withMessage("error.subscription.userIntegration").build(), e);
        }
    }

    private void updateSubscriptionStatus(CreateSubscriptionQueueMessage message, ErrorDetails errorDetails) {
        if (isSubscriptionExists(errorDetails)) {
            subscriptionStatusService.updateStatus(message.getUserId(), message.getCreateSubscriptionDTO().getCourseId(), SubscriptionStatus.Status.SUCCESS);
            return;
        }
        subscriptionStatusService.updateStatus(message.getUserId(), message.getCreateSubscriptionDTO().getCourseId(), SubscriptionStatus.Status.ERROR);
    }

    private static boolean isSubscriptionExists(ErrorDetails errorDetails) {
        return errorDetails.getMessage().getCode().equals(VALIDATION_SUBSCRIPTION_EXISTS)
                || errorDetails.getFieldErrors()
                .stream()
                .flatMap(apiFieldError -> apiFieldError.getMessages().stream())
                .map(APIMessage::getCode)
                .anyMatch(VALIDATION_SUBSCRIPTION_EXISTS::equals);
    }
}
