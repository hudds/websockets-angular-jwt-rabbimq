package dev.hudsonprojects.backend.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User")
@RequestMapping("user")
public class AppUserEndpoint {
	
	private final AppUserService appUserService;
	
	@Autowired
	public AppUserEndpoint(AppUserService appUserService) {
		this.appUserService = appUserService;
	}



	@Operation(description = "Retrieves the logged user data")
	@ApiResponse(responseCode = "200", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AppUserDTO.class)) })
	@ApiResponse(responseCode = "401", description = "Authentication required", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema()) })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public AppUserDTO info() {
		return appUserService.getLoggedUser();
	}

}
