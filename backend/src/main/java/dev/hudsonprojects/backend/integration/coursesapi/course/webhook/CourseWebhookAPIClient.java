package dev.hudsonprojects.backend.integration.coursesapi.course.webhook;

import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIAuthorizedHttpClient;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIHttpRequest;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.coursesapi.webhook.WebhookDTO;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


@Service
public class CourseWebhookAPIClient {

    private final CoursesAPIAuthorizedHttpClient client;

    @Autowired
    public CourseWebhookAPIClient(CoursesAPIAuthorizedHttpClient client) {
        this.client = client;
    }


    public WebhookDTO subscribe(CourseWebhookSubscriptionDTO subscription, IntegrationHttpProtocol integrationHttpProtocol) throws CoursesAPIHttpException {
        CoursesAPIHttpRequest httpRequest = CoursesAPIHttpRequest.builder()
                .setPath("course/webhook/subscribe")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .setBody(subscription)
                .build();
        return client.doPut(WebhookDTO.class, httpRequest, integrationHttpProtocol);
    }
}
