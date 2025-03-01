package dev.hudsonprojects.api.webhook.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hudsonprojects.api.common.exception.APIInternalErrorException;
import dev.hudsonprojects.api.webhook.entity.NotificationProtocolErrorType;
import dev.hudsonprojects.api.webhook.entity.WebhookNotificationHttpProtocol;
import dev.hudsonprojects.api.webhook.entity.WebhookNotificationProtocolStatus;
import dev.hudsonprojects.api.webhook.repository.WebhookNotificationHttpProtocolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class WebhookNotificationService {

    private static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private final WebhookNotificationHttpProtocolRepository webhookNotificationHttpProtocolRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public WebhookNotificationService(WebhookNotificationHttpProtocolRepository webhookNotificationHttpProtocolRepository, ObjectMapper objectMapper) {
        this.webhookNotificationHttpProtocolRepository = webhookNotificationHttpProtocolRepository;
        this.objectMapper = objectMapper;
    }

    public void sendNotification(WebhookNotification webhookNotification) {
        String body = null;
        if(webhookNotification.getBody() instanceof String){
            body = (String) webhookNotification.getBody();
            if (webhookNotification.getContentType() == null) {
                webhookNotification.setContentType(DEFAULT_MEDIA_TYPE);
            }
        } else if (webhookNotification.getBody() != null) {
            webhookNotification.setContentType(DEFAULT_MEDIA_TYPE);
            try {
                body = objectMapper.writeValueAsString(webhookNotification.getBody());
            } catch (JsonProcessingException e) {
                throw new APIInternalErrorException(e);
            }
        }
        if(webhookNotification.getHeaders() == null){
            webhookNotification.setHeaders(new HashMap<>());
        }
        webhookNotification.getHeaders().put("Content-Type", webhookNotification.getContentType());
        List<WebhookNotificationHttpProtocol> protocols = buildProtocols(webhookNotification, body);
        webhookNotificationHttpProtocolRepository.saveAll(protocols);
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            if (webhookNotification.getHeaders() != null) {
                webhookNotification.getHeaders().forEach(httpHeaders::set);
            }
            httpHeaders.setContentType(MediaType.parseMediaType(webhookNotification.getContentType()));
            HttpEntity<?> entity  = new HttpEntity<>(body, httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(webhookNotification.getUrl(), HttpMethod.valueOf(webhookNotification.getMethod()), entity, String.class);
            saveSuccessProtocol(protocols, response);
        } catch (HttpStatusCodeException e) {
            handleStatusCodeException(e, protocols);
            throw new APIInternalErrorException(e);
        } catch (ResourceAccessException e){
            for(var protocol : protocols) {
                protocol.setStatus(WebhookNotificationProtocolStatus.ERROR);
                protocol.setErrorType(NotificationProtocolErrorType.RESOURCE_ACCESS);
            }
            webhookNotificationHttpProtocolRepository.saveAll(protocols);
            throw new APIInternalErrorException(e);
        } catch (Exception e) {
            for(var protocol : protocols) {
                protocol.setStatus(WebhookNotificationProtocolStatus.ERROR);
                protocol.setErrorType(NotificationProtocolErrorType.UNKNOWN);
            }
            webhookNotificationHttpProtocolRepository.saveAll(protocols);
            throw new APIInternalErrorException(e);
        }
    }

    private void saveSuccessProtocol(List<WebhookNotificationHttpProtocol> protocols, ResponseEntity<String> response) {
        for(var protocol : protocols) {
            protocol.setResponseReceivedAt(LocalDateTime.now());
            String responseBody = response.getBody();
            if (responseBody != null) {
                protocol.setResponseBody(responseBody);
            }
            try {
                protocol.setResponseHeaders(objectMapper.writeValueAsString(response.getHeaders()));
            } catch (Exception e) {
                // ignored
            }
            protocol.setResponseStatusCode(response.getStatusCode().value());
            protocol.setStatus(WebhookNotificationProtocolStatus.SUCCESS);
            webhookNotificationHttpProtocolRepository.save(protocol);
        }
    }

    private void handleStatusCodeException(HttpStatusCodeException e, List<WebhookNotificationHttpProtocol> protocols) {
        for(var protocol : protocols) {
            protocol.setResponseReceivedAt(LocalDateTime.now());
            HttpStatusCode statusCode = e.getStatusCode();
            String responseBodyAs = e.getResponseBodyAs(String.class);
            protocol.setStatus(WebhookNotificationProtocolStatus.ERROR);
            protocol.setResponseBody(responseBodyAs);
            protocol.setResponseStatusCode(statusCode.value());
            protocol.setErrorType(NotificationProtocolErrorType.RESPONSE);
            webhookNotificationHttpProtocolRepository.save(protocol);
        }
    }


    private List<WebhookNotificationHttpProtocol> buildProtocols(WebhookNotification webhookNotification, String body) {
        List<WebhookNotificationHttpProtocol> protocols = new ArrayList<>();
        for(Long webhookId : webhookNotification.getWebhookIds()) {
            String protocolHeaders = null;

            if (webhookNotification.getHeaders() != null && !webhookNotification.getHeaders().isEmpty()) {
                try {
                    protocolHeaders = objectMapper.writeValueAsString(webhookNotification.getHeaders());
                } catch (JsonProcessingException e) {
                    throw new APIInternalErrorException(e);
                }
            }
            WebhookNotificationHttpProtocol protocol = new WebhookNotificationHttpProtocol();
            protocol.setMethod(webhookNotification.getMethod());
            protocol.setUrl(webhookNotification.getUrl());
            protocol.setStatus(WebhookNotificationProtocolStatus.PENDING);
            protocol.setSentAt(LocalDateTime.now());
            protocol.setBody(body);
            protocol.setHeaders(protocolHeaders);
            protocol.setWebhookId(webhookId);
            protocols.add(protocol);
        }
        return protocols;
    }

}
