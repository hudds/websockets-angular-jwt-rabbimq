package dev.hudsonprojects.backend.integration.coursesapi.course.webhook;

import dev.hudsonprojects.backend.integration.coursesapi.webhook.HttpHeaderDTO;

import java.util.List;

public class CourseWebhookSubscriptionDTO {

    private String url;
    private String method;
    private List<HttpHeaderDTO> headers;
    private boolean unsubscribeOtherWebhooks;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<HttpHeaderDTO> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeaderDTO> headers) {
        this.headers = headers;
    }

    public boolean isUnsubscribeOtherWebhooks() {
        return unsubscribeOtherWebhooks;
    }

    public void setUnsubscribeOtherWebhooks(boolean unsubscribeOtherWebhooks) {
        this.unsubscribeOtherWebhooks = unsubscribeOtherWebhooks;
    }
}
