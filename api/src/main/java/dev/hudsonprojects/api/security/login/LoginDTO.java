package dev.hudsonprojects.api.security.login;

import java.io.Serializable;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO implements Serializable{

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginDTO loginDTO = (LoginDTO) o;
        return Objects.equals(username, loginDTO.username) && Objects.equals(password, loginDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
