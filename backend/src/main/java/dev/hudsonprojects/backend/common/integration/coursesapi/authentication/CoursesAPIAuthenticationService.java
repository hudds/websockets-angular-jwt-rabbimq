package dev.hudsonprojects.backend.common.integration.coursesapi.authentication;

import dev.hudsonprojects.backend.common.integration.coursesapi.authentication.dto.LoginDTO;
import dev.hudsonprojects.backend.common.integration.coursesapi.authentication.dto.SuccessfulLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class CoursesAPIAuthenticationService {
    private final String apiUrl;
    private final String username;
    private final String password;

    private SuccessfulLoginDTO tokens;

    public CoursesAPIAuthenticationService(String apiUrl, String username, String password) {
        this.apiUrl = apiUrl;
        this.username = username;
        this.password = password;
    }




    private void authenticate() {
        ResponseEntity<SuccessfulLoginDTO> response = new RestTemplate().postForEntity(apiUrl + "/user/auth", new LoginDTO(username, password), SuccessfulLoginDTO.class);
        this.tokens = response.getBody();
    }

}
