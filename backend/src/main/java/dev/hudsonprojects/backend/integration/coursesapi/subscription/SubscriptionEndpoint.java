package dev.hudsonprojects.backend.integration.coursesapi.subscription;

import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsResolved;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("subscription")
public class SubscriptionEndpoint {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionEndpoint(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(description = "Subscribes the logged user to the course")
    @ApiResponse(responseCode = "200", description = "Return the subscription info", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppUserDTO.class)) })
    @ApiResponse(responseCode = "400", description = "Subscription invalid.", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema()) })
    @ApiResponse(responseCode = "401", description = "Authentication required", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema()) })
    @ApiResponse(responseCode = "404", description = "Course or user not found on API", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @ApiResponse(responseCode = "500", description = "Error communicating with course API", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
    @PostMapping(value = "course/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SubscriptionDTO subscribe(@PathVariable("courseId") Long courseId) {
        return subscriptionService.subscribe(courseId);
    }

}
