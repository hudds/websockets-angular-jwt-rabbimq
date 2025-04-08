package dev.hudsonprojects.api.webhook;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.person.PersonDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("webhook")
public class WebhookEndpoint {

    private final WebhookService webhookService;

    @Autowired
    public WebhookEndpoint(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @ApiResponse(responseCode = "200", description = "Returns all the webhooks from the logged user", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WebhookDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WebhookDTO> getWebhooksFromUser(){
        return webhookService.getWebhooksFromUser();
    }
}
