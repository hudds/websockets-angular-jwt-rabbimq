package dev.hudsonprojects.backend.integration.coursesapi.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.appuser.AppUserService;
import dev.hudsonprojects.backend.common.exception.APIErrorType;
import dev.hudsonprojects.backend.common.exception.InternalErrorException;
import dev.hudsonprojects.backend.common.exception.ValidationException;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.common.requestdata.RequestData;
import dev.hudsonprojects.backend.integration.coursesapi.error.ErrorDetailsAdapter;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.security.login.AppUserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class SubscriptionService {

    private final SubscriptionAPIClient subscriptionAPIClient;
    private final AppUserService appUserService;
    private final ObjectMapper objectMapper;

    public SubscriptionService(SubscriptionAPIClient subscriptionAPIClient, AppUserService appUserService, ObjectMapper objectMapper) {
        this.subscriptionAPIClient = subscriptionAPIClient;
        this.appUserService = appUserService;
        this.objectMapper = objectMapper;
    }


    public SubscriptionDTO subscribe(Long courseId) {
        if(courseId == null){
            throw new ValidationException(ErrorDetailsBuilder.withMessage("validation.subscription.course.required").build());
        }
        AppUserDTO loggedUser = appUserService.getLoggedUser();
        CreateSubscriptionDTO createSubscriptionDTO = new CreateSubscriptionDTO();
        createSubscriptionDTO.setCpf(loggedUser.getCpf());
        createSubscriptionDTO.setCourseId(courseId);
        try {
            return subscriptionAPIClient.subscribe(createSubscriptionDTO);
        } catch (CoursesAPIHttpException e) {
            Optional<ErrorDetails> errorDetails = translateAPIMessage(e.getStatusCode(), e.getResponseBody());
            if(errorDetails.isPresent()){
                throw new ValidationException(errorDetails.get());
            }
            throw new InternalErrorException(ErrorDetailsBuilder.withMessage("error.subscription").build());
        }
    }

    private Optional<ErrorDetails> translateAPIMessage(Integer statusCode, String responseBody) {
        if(statusCode == null || StringUtils.isBlank(responseBody) || (statusCode != 400 && statusCode != 404)){
            return Optional.empty();
        }
        ErrorDetailsAdapter errorDetailsAdapter = new ErrorDetailsAdapter(objectMapper, Set.of("validation.subscription.exists", "validation.subscription.course.overbooking"));
        return errorDetailsAdapter.adapt(APIErrorType.VALIDATION_ERROR, responseBody);
    }
}
