package dev.hudsonprojects.api.webhook;

import dev.hudsonprojects.api.appuser.AppUserService;
import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.common.messages.APIMessage;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.common.validation.GenericValidator;
import dev.hudsonprojects.api.webhook.entity.HttpRequestData;
import dev.hudsonprojects.api.webhook.entity.Webhook;
import dev.hudsonprojects.api.webhook.entity.WebhookTopic;
import dev.hudsonprojects.api.webhook.repository.WebhookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class WebhookValidationService {

    private final WebhookRepository webhookRepository;
    private final AppUserService appUserService;


    @Autowired
    public WebhookValidationService(WebhookRepository webhookRepository, AppUserService appUserService) {
        this.webhookRepository = webhookRepository;
        this.appUserService = appUserService;
    }

    private static Boolean validUrl(HttpRequestData httpRequestData) {
        if(httpRequestData == null || StringUtils.isBlank(httpRequestData.getUrl())){
           return false;
        }
        try {
            URI uri = new URI(httpRequestData.getUrl());
            uri.toURL();
            return uri.isAbsolute();
        } catch (URISyntaxException | MalformedURLException | IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean methodValid(HttpRequestData httpRequestData) {
        if (StringUtils.isBlank(httpRequestData.getMethod())) {
            return false;
        }
        return Stream.of(HttpMethod.values()).map(String::valueOf).anyMatch(method -> method.equals(httpRequestData.getMethod()));
    }


    public List<APIFieldError> validateCreation(Webhook webhook) {
        List<APIFieldError> errors = getCreationValuesValidator().applyValidations(webhook);
        if(errors.isEmpty()){
            errors.addAll(getCreationHttpRequestValuesValidator().applyValidations(webhook.getHttpRequestData()));
        }
        if(errors.isEmpty() && webhook.getWebhookId() == null){
            validateExistence(webhook);
        }
        return errors;
    }

    public List<APIFieldError> validateUpdate(Webhook webhook) {
        List<APIFieldError> errors =  new ArrayList<>();
        if(webhook.getWebhookId() == null){
            errors.add(new APIFieldError("webhookId", new APIMessage("validation.webhook.id.required")));
            return errors;
        }
        errors.addAll(validateCreation(webhook));
        if (errors.isEmpty()) {
            errors.addAll(validateExistence(webhook));
        }
        return errors;
    }

    private List<APIFieldError> validateExistence(Webhook webhook) {
        List<APIFieldError> errors = new ArrayList<>();
        APIMessage message = new APIMessage("validation.webhook.exists");
        if (exists(webhook)) {
            errors.add(new APIFieldError("url", message));
            errors.add(new APIFieldError("method", message));
            errors.add(new APIFieldError("ownerId", message));
        }
        return errors;
    }

    private boolean exists(Webhook webhook) {
        if (webhook.getWebhookId() != null) {
            return webhookRepository.existsByOwnerIdAndUrlAndMethodAndNotWebhookId(webhook.getOwnerId(), webhook.getHttpRequestData().getUrl(), webhook.getHttpRequestData().getMethod(), webhook.getWebhookId());
        }
        return webhookRepository.existsByOwnerIdAndUrlAndMethod(webhook.getOwnerId(), webhook.getHttpRequestData().getUrl(), webhook.getHttpRequestData().getMethod());
    }

    public List<APIFieldError> validate(HttpRequestData httpRequestData) {
      return getCreationHttpRequestValuesValidator().applyValidations(httpRequestData);
    }


    public GenericValidator<Webhook> getCreationValuesValidator() {
        return GenericValidator.builder(Webhook.class)
                .addValidation(
                        "ownerId",
                        "NotNull",
                        webhook -> webhook.getOwnerId() != null)
                .addValidation(
                        "httpRequestData",
                        "NotNull",
                        webhook -> webhook.getHttpRequestData() != null)
                .addValidation(
                        "topic",
                        "NotEmpty",
                        WebhookValidationService::topicsValid
                )
                .build();
    }

    public GenericValidator<HttpRequestData> getCreationHttpRequestValuesValidator() {
        return GenericValidator.builder(HttpRequestData.class).addValidation(
                "httpRequestData.url",
                "NotBlank",
                httpRequestData -> httpRequestData != null && StringUtils.isNotBlank(httpRequestData.getUrl())
        ).addValidation(
                "httpRequestData.url",
                "validation.webhook.url.invalid",
                WebhookValidationService::validUrl
        ).addValidation(
                "httpRequestData.method",
                "NotBlank",
                httpRequestData -> StringUtils.isNotBlank(httpRequestData.getMethod())
        ).addValidation(
                "httpRequestData.method",
                "validation.webhook.method.invalid",
                WebhookValidationService::methodValid
        ).addValidation(
                "httpRequestData.headers.name",
                "NotBlank",
                httpRequestData -> httpRequestData.getHeaders() == null || httpRequestData.getHeaders().stream().noneMatch(httpHeader -> httpHeader == null || StringUtils.isBlank(httpHeader.getName()))
        ).build();
    }

    private static boolean topicsValid(Webhook webhook) {
        return webhook.getTopics() != null &&
                !webhook.getTopics().isEmpty() &&
                webhook.getTopics().stream().noneMatch(Objects::isNull) &&
                webhook.getTopics().stream().map(WebhookTopic::getTopic).noneMatch(StringUtils::isBlank);
    }
}
