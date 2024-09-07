package dev.hudsonprojects.backend.security.login;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO implements Serializable{

    @NotBlank
    private String cpfOrEmail;
    @NotBlank
    private String password;
    
    public String getCpfOrEmail() {
        return cpfOrEmail;
    }
    public void setCpfOrEmail(String cpfOrEmail) {
        this.cpfOrEmail = cpfOrEmail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    

}
