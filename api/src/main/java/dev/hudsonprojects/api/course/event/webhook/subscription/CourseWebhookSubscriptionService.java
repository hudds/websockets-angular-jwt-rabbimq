package dev.hudsonprojects.api.course.event.webhook.subscription;

import dev.hudsonprojects.api.common.lib.util.StringUtils;
import dev.hudsonprojects.api.common.requestdata.RequestData;
import dev.hudsonprojects.api.security.login.AppUserDetails;
import dev.hudsonprojects.api.webhook.CreateWebhookDTO;
import dev.hudsonprojects.api.webhook.UpdateWebhookDTO;
import dev.hudsonprojects.api.webhook.WebhookDTO;
import dev.hudsonprojects.api.webhook.WebhookService;
import dev.hudsonprojects.api.webhook.entity.Webhook;
import dev.hudsonprojects.api.webhook.repository.WebhookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static dev.hudsonprojects.api.course.event.CourseEvent.TOPIC_NAME;

@Service
public class CourseWebhookSubscriptionService {


    private final WebhookRepository webhookRepository;
    private final WebhookService webhookService;
    private final RequestData requestData;


    @Autowired
    public CourseWebhookSubscriptionService(WebhookRepository webhookRepository, WebhookService webhookService, RequestData requestData) {
        this.webhookRepository = webhookRepository;
        this.webhookService = webhookService;
        this.requestData = requestData;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public WebhookDTO subscribe(CourseWebhookSubscriptionDTO courseWebhookSubscriptionDTO) {
        AppUserDetails user = requestData.getUserOrUnauthorized();
        if(StringUtils.isBlank(courseWebhookSubscriptionDTO.getUrl()) || StringUtils.isBlank(courseWebhookSubscriptionDTO.getMethod())){
            return webhookService.createWebhook(courseWebhookSubscriptionDTO.toCreateWebhookDTO());
        }
        Optional<Webhook> existingWebhook = webhookRepository.findByOwnerIdAndUrlAndMethod(user.getUserId(), courseWebhookSubscriptionDTO.getUrl(), courseWebhookSubscriptionDTO.getMethod());
        WebhookDTO webhookDTO;
        if (existingWebhook.isPresent()) {
            UpdateWebhookDTO updateWebhookDTO = new UpdateWebhookDTO(existingWebhook.get());
            fillWebhookSubscriptionValues(courseWebhookSubscriptionDTO, updateWebhookDTO, true);
            webhookDTO = webhookService.updateWebhook(updateWebhookDTO);
        } else {
            CreateWebhookDTO createWebhookDTO = new CreateWebhookDTO();
            fillWebhookSubscriptionValues(courseWebhookSubscriptionDTO, createWebhookDTO, true);
            webhookDTO = webhookService.createWebhook(createWebhookDTO);
        }
        if(courseWebhookSubscriptionDTO.isUnsubscribeOtherWebhooks()) {
            webhookService.unsubscribeAllWebhooksFromTopicExcept(TOPIC_NAME, webhookDTO.getWebhookId());
        }
        return webhookDTO;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Optional<WebhookDTO> unsubscribe(CourseWebhookSubscriptionDTO courseWebhookSubscriptionDTO) {
        AppUserDetails user = requestData.getUserOrUnauthorized();
        if(StringUtils.isBlank(courseWebhookSubscriptionDTO.getUrl()) || StringUtils.isBlank(courseWebhookSubscriptionDTO.getMethod())){
            return Optional.empty();
        }
        Optional<Webhook> existingWebhook = webhookRepository.findByOwnerIdAndUrlAndMethod(user.getUserId(), courseWebhookSubscriptionDTO.getUrl(), courseWebhookSubscriptionDTO.getMethod());
        if (existingWebhook.isPresent() && existingWebhook.get().getTopics().stream().anyMatch(topic -> TOPIC_NAME.equals(topic.getTopic()))) {
            UpdateWebhookDTO updateWebhookDTO = new UpdateWebhookDTO(existingWebhook.get());
            fillWebhookSubscriptionValues(courseWebhookSubscriptionDTO, updateWebhookDTO);
            updateWebhookDTO.getTopics().removeIf(TOPIC_NAME::equals);
            return Optional.ofNullable(webhookService.updateWebhook(updateWebhookDTO));
        }
        return existingWebhook.map(WebhookDTO::new);
    }

    private static void fillWebhookSubscriptionValues(CourseWebhookSubscriptionDTO courseWebhookSubscriptionDTO, CreateWebhookDTO createWebhookDTO) {
        fillWebhookSubscriptionValues(courseWebhookSubscriptionDTO, createWebhookDTO, null);
    }

    private static void fillWebhookSubscriptionValues(CourseWebhookSubscriptionDTO courseWebhookSubscriptionDTO, CreateWebhookDTO createWebhookDTO, Boolean active) {
        if(active != null) {
            createWebhookDTO.setActive(active);
        }
        createWebhookDTO.getTopics().add(TOPIC_NAME);
        courseWebhookSubscriptionDTO.copyValuesTo(createWebhookDTO);
    }
}
