package dev.hudsonprojects.api.course.event.webhook.subscription;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.webhook.WebhookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("course/webhook")
@Tag(name = "Course Webhook")
public class CourseWebhookSubscriptionEndpoint {


    private final CourseWebhookSubscriptionService courseWebhookSubscriptionService;

    @Autowired
    public CourseWebhookSubscriptionEndpoint(CourseWebhookSubscriptionService courseWebhookSubscriptionService) {
        this.courseWebhookSubscriptionService = courseWebhookSubscriptionService;
    }

    @Operation(description = """
            Subscribes a webhook to the "course" topic. Only one combination of URL and method is allowed per user on all topics.
            If the user already have a webhook with the same URL and method, the topic will be added to the existing webhook.
            """)
    @ApiResponse(responseCode = "200", description = "Webhook created or updated", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WebhookDTO.class)) })
    @ApiResponse(responseCode = "400", description = "Invalid information", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @PutMapping(value = "subscribe")
    public WebhookDTO subscribe(@RequestBody @Valid CourseWebhookSubscriptionDTO courseWebhookSubscriptionDTO){
        return courseWebhookSubscriptionService.subscribe(courseWebhookSubscriptionDTO);
    }

    @Operation(description = """
            Unsubscribes a webhook from the "course" topic.
            """)
    @ApiResponse(responseCode = "200", description = "Webhook updated", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WebhookDTO.class)) })
    @ApiResponse(responseCode = "400", description = "Invalid information", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "404", description = "Webhook not found", content = {@Content() })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @PutMapping(value = "unsubscribe")
    public ResponseEntity<WebhookDTO> unsubscribe(@RequestBody @Valid CourseWebhookSubscriptionDTO courseWebhookSubscriptionDTO){
        Optional<WebhookDTO> webhook = courseWebhookSubscriptionService.unsubscribe(courseWebhookSubscriptionDTO);
        return ResponseEntity.ofNullable(webhook.orElse(null));
    }
}
