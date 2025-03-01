package dev.hudsonprojects.api.course.event.webhook.subscription;

import dev.hudsonprojects.api.webhook.CreateWebhookDTO;
import dev.hudsonprojects.api.webhook.HttpHeaderDTO;
import dev.hudsonprojects.api.webhook.UpdateWebhookDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public class CourseWebhookSubscriptionDTO {

    @NotBlank
    private String url;
    @NotBlank
    private String method;
    @Valid
    private List<HttpHeaderDTO> headers;
    private boolean unsubscribeOtherWebhooks;

    public @NotBlank String getUrl() {
        return url;
    }

    public void setUrl(@NotBlank String url) {
        this.url = url;
    }

    public @NotBlank String getMethod() {
        return method;
    }

    public void setMethod(@NotBlank String method) {
        this.method = method == null ? null : method.toUpperCase();
    }

    public @Valid List<HttpHeaderDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(@Valid List<HttpHeaderDTO> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseWebhookSubscriptionDTO that = (CourseWebhookSubscriptionDTO) o;
        return Objects.equals(url, that.url) && Objects.equals(method, that.method) && Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method, headers);
    }

    public CreateWebhookDTO toCreateWebhookDTO() {
        CreateWebhookDTO createWebhookDTO = new CreateWebhookDTO();
        copyValuesTo(createWebhookDTO);
        return createWebhookDTO;
    }

    public UpdateWebhookDTO toUpdateWebhookDTO() {
        UpdateWebhookDTO updateWebhookDTO = new UpdateWebhookDTO();
        copyValuesTo(updateWebhookDTO);
        return updateWebhookDTO;
    }

    public void copyValuesTo(CreateWebhookDTO createWebhookDTO) {
        createWebhookDTO.setUrl(url);
        createWebhookDTO.setHeaders(headers);
        createWebhookDTO.setMethod(method);
    }

    public boolean isUnsubscribeOtherWebhooks() {
        return unsubscribeOtherWebhooks;
    }

    public void setUnsubscribeOtherWebhooks(boolean unsubscribeOtherWebhooks) {
        this.unsubscribeOtherWebhooks = unsubscribeOtherWebhooks;
    }
}
