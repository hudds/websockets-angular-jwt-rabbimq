package dev.hudsonprojects.backend.endpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hudsonprojects.backend.dto.UserRegistrationDTO;
import dev.hudsonprojects.backend.service.UserRegistrationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/registration")
public class RegistrationEndpoint {

    private final UserRegistrationService userRegistrationService;

    public RegistrationEndpoint(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public void register(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){
        userRegistrationService.registerUser(userRegistrationDTO);
    }

}
