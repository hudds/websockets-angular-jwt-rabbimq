package dev.hudsonprojects.backend.integration.coursesapi.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.backend.common.exception.APIErrorType;
import dev.hudsonprojects.backend.common.exception.APIMessageException;
import dev.hudsonprojects.backend.common.exception.InternalErrorException;
import dev.hudsonprojects.backend.common.exception.ValidationException;
import dev.hudsonprojects.backend.common.lib.util.StringUtils;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetails;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIAuthorizedHttpClient;
import dev.hudsonprojects.backend.integration.coursesapi.client.CoursesAPIHttpRequest;
import dev.hudsonprojects.backend.integration.coursesapi.error.ErrorDetailsAdapter;
import dev.hudsonprojects.backend.integration.coursesapi.exception.CoursesAPIHttpException;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocol;
import dev.hudsonprojects.backend.integration.protocol.IntegrationHttpProtocolRepository;
import dev.hudsonprojects.backend.integration.protocol.IntegrationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class SubscriptionAPIClient {

    private final CoursesAPIAuthorizedHttpClient client;
    private final IntegrationHttpProtocolRepository integrationHttpProtocolRepository;
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(SubscriptionAPIClient.class);

    @Autowired
    public SubscriptionAPIClient(CoursesAPIAuthorizedHttpClient client, IntegrationHttpProtocolRepository integrationHttpProtocolRepository, ObjectMapper objectMapper) {
        this.client = client;
        this.integrationHttpProtocolRepository = integrationHttpProtocolRepository;
        this.objectMapper = objectMapper;
    }

    public SubscriptionDTO subscribe(CreateSubscriptionDTO subscription) throws CoursesAPIHttpException {
        IntegrationHttpProtocol protocol = new IntegrationHttpProtocol();
        protocol.setIntegrationStatus(IntegrationStatus.PENDING);
        integrationHttpProtocolRepository.save(protocol);
        try {
            SubscriptionDTO subscriptionCreated = client.doPost(SubscriptionDTO.class, CoursesAPIHttpRequest.builder()
                    .setPath("subscription")
                    .setBody(subscription)
                    .build(), protocol);
            protocol.setIntegrationStatus(IntegrationStatus.SUCCESS);
            integrationHttpProtocolRepository.save(protocol);
            return subscriptionCreated;
        } catch (CoursesAPIHttpException e) {
            String responseBody = e.getResponseBody();
            protocol.setResponse(responseBody);
            protocol.setResponseReceivedAt(LocalDateTime.now());
            protocol.setResponseStatus(e.getStatusCode());
            protocol.setIntegrationStatus(IntegrationStatus.ERROR);
            protocol.setExceptionMessage(e.getMessage());
            integrationHttpProtocolRepository.save(protocol);
            throw e;
        }
    }



}
