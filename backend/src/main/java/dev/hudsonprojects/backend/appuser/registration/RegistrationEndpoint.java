package dev.hudsonprojects.backend.appuser.registration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hudsonprojects.backend.appuser.AppUserDTO;
import dev.hudsonprojects.backend.common.messages.error.errordetails.ErrorDetailsResolved;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/registration")
@Tag(name = "User registration")
public class RegistrationEndpoint {

	private final UserRegistrationService userRegistrationService;

	public RegistrationEndpoint(UserRegistrationService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}

	@Operation(description = "Create a new user")
	@ApiResponse(responseCode = "200", description = "User created", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppUserDTO.class)) })
	@ApiResponse(responseCode = "400", description = "Invalid data", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUserDTO register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
		return userRegistrationService.registerUser(userRegistrationDTO);
	}

}
