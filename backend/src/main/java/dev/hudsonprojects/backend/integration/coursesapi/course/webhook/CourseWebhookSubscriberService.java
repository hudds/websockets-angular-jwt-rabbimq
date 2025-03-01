package dev.hudsonprojects.backend.integration.coursesapi.course.webhook;

import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.webhook.HttpHeaderDTO;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocol;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocolRepository;
import dev.hudsonprojects.backend.integration.protocol.IntegrationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseWebhookSubscriberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseWebhookSubscriberService.class);

    public static final String WEBHOOK_LISTENER_COURSE_URI = "webhook/listener/course";
    @Value("${integration.courses_api.webhook.course.token}")
    private String listenerToken;

    @Value("${integration.courses_api.webhook.course.host}")
    private String listenerHost;

    private final CourseWebhookAPIClient courseWebhookAPIClient;
    private final IntegrationHttpProtocolRepository integrationHttpProtocolRepository;

    public CourseWebhookSubscriberService(CourseWebhookAPIClient courseWebhookAPIClient, IntegrationHttpProtocolRepository integrationHttpProtocolRepository) {
        this.courseWebhookAPIClient = courseWebhookAPIClient;
        this.integrationHttpProtocolRepository = integrationHttpProtocolRepository;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void subscribeCourseTopic() throws CoursesAPIHttpException {
        IntegrationHttpProtocol protocol = createPendingProtocol();

        CourseWebhookSubscriptionDTO subscription = new CourseWebhookSubscriptionDTO();
        subscription.setHeaders(List.of(new HttpHeaderDTO("token", listenerToken)));
        String url = listenerHost + "/" + WEBHOOK_LISTENER_COURSE_URI;
        subscription.setUrl(url);
        subscription.setUnsubscribeOtherWebhooks(true);
        String method = "POST";
        subscription.setMethod(method);
        try {
            courseWebhookAPIClient.subscribe(subscription, protocol);
            handleSuccess(protocol);
            LOGGER.info("Webhook {} {} registered to course topic", method, url);
        } catch (CoursesAPIHttpException e){
            handleHttpErrorProtocol(e, protocol);
            LOGGER.error("Error on subscribing to course webhook", e);
        } catch (Exception e){
            handleExceptionProtocol(e, protocol);
            LOGGER.error("Error on subscribing to course webhook", e);
        }

    }

    private void handleSuccess(IntegrationHttpProtocol protocol) {
        if(protocol != null) {
            protocol.setResponseReceivedAt(LocalDateTime.now());
            protocol.setIntegrationStatus(IntegrationStatus.SUCCESS);
            tryUpdateProtocol(protocol);
        }
    }

    private void tryUpdateProtocol(IntegrationHttpProtocol protocol) {
        try {
            integrationHttpProtocolRepository.save(protocol);
        } catch (Exception e) {
            LOGGER.error("Error on updating protocol", e);
        }
    }

    private IntegrationHttpProtocol createPendingProtocol() {
        try {
            IntegrationHttpProtocol protocol = new IntegrationHttpProtocol();
            protocol.setIntegrationStatus(IntegrationStatus.PENDING);
            integrationHttpProtocolRepository.save(protocol);
            return protocol;
        } catch (Exception e){
            LOGGER.error("Error on saving integration protocol during course webhook subscription", e);
            return null;
        }

    }

    private void handleExceptionProtocol(Exception e, IntegrationHttpProtocol protocol) {
        if(protocol != null) {
            protocol.setResponseReceivedAt(LocalDateTime.now());
            protocol.setExceptionMessage(e.getMessage());
            protocol.setIntegrationStatus(IntegrationStatus.ERROR);
            tryUpdateProtocol(protocol);
        }
    }

    private void handleHttpErrorProtocol(CoursesAPIHttpException e, IntegrationHttpProtocol protocol) {
        if(protocol != null) {
            protocol.setResponseReceivedAt(LocalDateTime.now());
            protocol.setExceptionMessage(e.getMessage());
            protocol.setIntegrationStatus(IntegrationStatus.ERROR);
            if(e.getResponseBody() != null){
                protocol.setResponse(e.getResponseBody());
            }
            protocol.setResponseStatus(e.getStatusCode());
            tryUpdateProtocol(protocol);
        }
    }
}
