package dev.hudsonprojects.backend.security.login;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController()
@Tag(name= "Authentication")
@RequestMapping("/user/auth")
public class AuthenticationEndpoint {
	
	private final AppUserLoginService appUserLoginService;
	
	public AuthenticationEndpoint(AppUserLoginService appUserLoginService) {
		this.appUserLoginService = appUserLoginService;
	}

	
	@Operation(description = "Generates an access token and a refresh token")
	@ApiResponse(responseCode = "200", description = "Successfully authenticated", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessfulLoginDTO.class)) })
	@ApiResponse(responseCode = "401", description = "Email or CPF or password are incorrect", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema()) })
	@PostMapping
	public SuccessfulLoginDTO authenticate(@Valid @RequestBody LoginDTO login) {
		return appUserLoginService.authenticate(login);
	}
	

}
