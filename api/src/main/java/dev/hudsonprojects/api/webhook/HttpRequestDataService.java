package dev.hudsonprojects.api.webhook;

import dev.hudsonprojects.api.common.exception.ValidationException;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.fielderror.APIFieldError;
import dev.hudsonprojects.api.webhook.entity.HttpRequestData;
import dev.hudsonprojects.api.webhook.repository.HttpHeaderRepository;
import dev.hudsonprojects.api.webhook.repository.HttpRequestDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HttpRequestDataService {

    private final WebhookValidationService webhookValidationService;
    private final HttpRequestDataRepository httpRequestDataRepository;
    private final HttpHeaderRepository httpHeaderRepository;

    public HttpRequestDataService(WebhookValidationService webhookValidationService, HttpRequestDataRepository httpRequestDataRepository, HttpHeaderRepository httpHeaderRepository) {
        this.webhookValidationService = webhookValidationService;
        this.httpRequestDataRepository = httpRequestDataRepository;
        this.httpHeaderRepository = httpHeaderRepository;
    }

    @Transactional
    public void save(HttpRequestData httpRequestData){
        List<APIFieldError> errors = webhookValidationService.validate(httpRequestData);
        if(!errors.isEmpty()){
            throw new ValidationException(ErrorDetailsBuilder.withAPIFieldErrors(errors).build());
        }
        if(httpRequestData.getHttpRequestDataId() != null) {
            httpHeaderRepository.deleteByHttpRequestDataId(httpRequestData.getHttpRequestDataId());
        }
        httpRequestData.setUpdatedAt(LocalDateTime.now());
        httpRequestDataRepository.save(httpRequestData);
        httpRequestData.getHeaders().forEach(httpHeader -> httpHeader.setHttpRequestDataId(httpRequestData.getHttpRequestDataId()));
        httpRequestData.getHeaders().forEach(httpHeaderRepository::save);
    }
}
