package dev.hudsonprojects.api.webhook;

import dev.hudsonprojects.api.webhook.entity.Webhook;


public class UpdateWebhookDTO extends CreateWebhookDTO {
    private Long webhookId;

    public UpdateWebhookDTO() {}

    public UpdateWebhookDTO(Webhook webhook) {
        super(webhook);
        setWebhookId(webhook.getWebhookId());
    }

    public Long getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(Long webhookId) {
        this.webhookId = webhookId;
    }

    public void copyValuesTo(Webhook webhook){
        super.copyValuesTo(webhook);
        webhook.setWebhookId(webhookId);
    }
}
