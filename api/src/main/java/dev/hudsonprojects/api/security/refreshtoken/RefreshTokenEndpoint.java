package dev.hudsonprojects.api.security.refreshtoken;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hudsonprojects.api.common.exception.UnauthorizedException;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsBuilder;
import dev.hudsonprojects.api.common.messages.error.errordetails.ErrorDetailsResolved;
import dev.hudsonprojects.api.security.login.SuccessfulLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController()
@Tag(name= "Refresh token")
@RequestMapping("/user/auth/refresh-token")
public class RefreshTokenEndpoint {
	
	private final RefreshTokenService refreshTokenService;
	
	public RefreshTokenEndpoint(RefreshTokenService refreshTokenService) {
		this.refreshTokenService = refreshTokenService;
	}

	@Operation(description = "Generates a new access token and refresh token. The token used in this operation is invalidated and cannot be used anymore")
	@ApiResponse(responseCode = "200", description = "New token generated", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SuccessfulLoginDTO.class)) })
	@ApiResponse(responseCode = "401", description = "Did not found a valid token", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetailsResolved.class)) })
	@GetMapping
	public SuccessfulLoginDTO refreshToken(@Valid @NotBlank @RequestHeader("refresh-token") String token) {
		SuccessfulLoginDTO refreshToken = refreshTokenService.refreshToken(token);
		if(refreshToken == null) {
			throw new UnauthorizedException(ErrorDetailsBuilder.buildWithMessage("authentication.refreshtoken.invalid"));
		}
		return refreshToken;
	}
	
	@Operation(description = "Invalidates a refresh token if exists and is valid")
	@ApiResponse(responseCode = "200", description = "This operation is imdepotent. It will always return success.")
	@DeleteMapping
	public void revokeToken(@Valid @NotBlank @RequestHeader("refresh-token") String token) {
		refreshTokenService.revokeToken(token);
	}

}
