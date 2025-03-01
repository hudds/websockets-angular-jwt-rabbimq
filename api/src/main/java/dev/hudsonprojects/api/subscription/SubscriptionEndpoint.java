package dev.hudsonprojects.api.subscription;

import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.person.PersonDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subscription")
@Tag(name = "Inscrição")
public class SubscriptionEndpoint {


    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionEndpoint(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @ApiResponse(responseCode = "200", description = "Subscribe person to course and return the subscription data.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class)) })
    @ApiResponse(responseCode = "401", description = "Authentication needed", content = {
            @Content() })
    @ApiResponse(responseCode = "400", description = "Invalid subscription data", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "404", description = "Person or Course not found", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SubscriptionDTO subscribe(@RequestBody @Valid CreateSubscriptionDTO createSubscriptionDTO){
        return subscriptionService.subscribe(createSubscriptionDTO);
    }

}
