package dev.hudsonprojects.api.webhook;

import dev.hudsonprojects.api.common.exception.NotFoundException;
import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.common.requestdata.RequestData;
import dev.hudsonprojects.api.security.login.AppUserDetails;
import dev.hudsonprojects.api.webhook.entity.HttpRequestData;
import dev.hudsonprojects.api.webhook.entity.Webhook;
import dev.hudsonprojects.api.webhook.repository.WebhookRepository;
import dev.hudsonprojects.api.webhook.repository.WebhookTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WebhookService {
    private final RequestData requestData;
    private final WebhookRepository webhookRepository;
    private final WebhookValidationService webhookValidationService;
    private final HttpRequestDataService httpRequestDataService;
    private final WebhookTopicRepository webhookTopicRepository;

    @Autowired
    public WebhookService(RequestData requestData, WebhookRepository webhookRepository, WebhookValidationService webhookValidationService, HttpRequestDataService httpRequestDataService, WebhookTopicRepository webhookTopicRepository) {
        this.requestData = requestData;
        this.webhookRepository = webhookRepository;
        this.webhookValidationService = webhookValidationService;
        this.httpRequestDataService = httpRequestDataService;
        this.webhookTopicRepository = webhookTopicRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public WebhookDTO createWebhook(CreateWebhookDTO dto) {
        AppUserDetails owner = requestData.getUserOrUnauthorized();
        Webhook webhook = dto.toWebhook();
        webhook.setOwnerId(owner.getUserId());
        List<APIFieldError> errors = webhookValidationService.validateCreation(webhook);
        if(!errors.isEmpty()){
            throw new ValidationException(ErrorDetailsBuilder.withAPIFieldErrors(errors).build());
        }
        HttpRequestData httpRequestData = webhook.getHttpRequestData();
        webhook.setUpdatedAt(LocalDateTime.now());
        httpRequestDataService.save(httpRequestData);
        webhookRepository.save(webhook);
        webhook.getTopics().forEach(webhookTopic -> webhookTopic.getId().setWebhookId(webhook.getWebhookId()));
        webhookTopicRepository.saveAll(webhook.getTopics());
        return new WebhookDTO(webhook);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public WebhookDTO updateWebhook(UpdateWebhookDTO dto) {
        AppUserDetails user = requestData.getUserOrUnauthorized();
        Webhook webhook = webhookRepository.findByWebhookIdAndOwnerId(dto.getWebhookId(), user.getUserId())
                .map(Webhook::new)
                .orElseThrow(() -> new NotFoundException(ErrorDetailsBuilder.withMessage("validation.webhook.notFound").build()));
        dto.copyValuesTo(webhook);
        List<APIFieldError> errors = webhookValidationService.validateUpdate(webhook);
        if(!errors.isEmpty()){
            throw new ValidationException(ErrorDetailsBuilder.withAPIFieldErrors(errors).build());
        }
        HttpRequestData httpRequestData = webhook.getHttpRequestData();
        httpRequestDataService.save(httpRequestData);
        webhookTopicRepository.flush();
        webhookRepository.save(webhook);
        return new WebhookDTO(webhook);
    }

    @Transactional
    public void unsubscribeAllWebhooksFromTopicExcept(String topic, Long webhookId) {
        if(topic == null || webhookId == null){
            return;
        }
        Long ownerId = requestData.getUserOrUnauthorized().getUserId();
        webhookTopicRepository.deleteByTopicAndOwnerIdAndNotWebhookId(topic, ownerId, webhookId);
    }

    @Transactional(readOnly = true)
    public List<WebhookDTO> getWebhooksFromUser() {
        Long userId = requestData.getUserOrUnauthorized().getUserId();
        List<Webhook> webhooks =  webhookRepository.findByOwnerId(userId);
        return webhooks.stream().map(WebhookDTO::new).toList();
    }
}
